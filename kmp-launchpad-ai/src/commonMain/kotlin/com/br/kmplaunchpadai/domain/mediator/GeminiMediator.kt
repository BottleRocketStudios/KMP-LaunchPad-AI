package com.br.kmplaunchpadai.domain.mediator

import com.br.kmplaunchpadai.data.converters.toDto
import com.br.kmplaunchpadai.data.converters.toFunctionDeclarationDto
import com.br.kmplaunchpadai.data.model.ConversationRequestDto
import com.br.kmplaunchpadai.data.model.ToolDto
import com.br.kmplaunchpadai.data.network.GeminiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class GeminiMediator(private val scope: CoroutineScope) {
    // Service
    private val geminiService = GeminiService()

    // State
    private val geminiFunctions: MutableList<GeminiFunction> = mutableListOf()
    private val mutex = Mutex()
    private val _conversationFlow = MutableStateFlow(
        GeminiContent(MODEL, GeminiPart(text = "Welcome to das BOT:"))
    )

    // Input flow
    val user: MutableStateFlow<String> = MutableStateFlow("")

    // Output
    val conversation = _conversationFlow
        .runningFold(emptyList<GeminiContent>()) { accumulator, value -> accumulator + value }
            .stateIn(scope, SharingStarted.Lazily, emptyList())

    private val _errorString: MutableStateFlow<String> = MutableStateFlow("")
    val errorString: StateFlow<String> = _errorString

    // Methods
    operator fun invoke(init: GeminiMediator.() -> Unit) = init()

    fun functionDeclaration(init: GeminiFunction.() -> Unit) =
        geminiFunctions.add(GeminiFunction().apply(init))


    suspend fun startChat() {
        user.collect { userInput ->
            val content = GeminiContent(USER, GeminiPart(text = userInput))
            _conversationFlow.emit(content)
            mutex.withLock {
                callGemini()
            }
        }
    }

    private suspend fun callGemini() {
//        TODO - Add escape logic to prevent infinite loop

        geminiService.callGemini(createConversationRequestDto()).onSuccess { response ->

            response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text?.let {
                val element = GeminiContent(
                    role = "model",
                    part = GeminiPart(text = it)
                )
                _conversationFlow.value = element
            }

            response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.functionCall?.let { functionCall ->
                _conversationFlow.value = GeminiContent(
                    role = "model",
                    part = GeminiPart(
                        functionCall = GeminiFunctionCall(
                            name = functionCall.name,
                            args = functionCall.args
                        )
                    )
                )

                val functionResponse = geminiFunctions.find { it.name == functionCall.name }?.call(
                    functionCall.args
                )
                _conversationFlow.value = GeminiContent(
                    role = "model",
                    part = GeminiPart(
                        functionResponse = GeminiResponse(
                            name = functionCall.name,
                            content = functionResponse
                        )
                    )
                )

                callGemini()
            }
        }.onFailure {
            _errorString.value = it.message.toString()
        }
    }

    private fun createConversationRequestDto() =
        ConversationRequestDto(
            conversation.value.toDto(),
            listOf(ToolDto(geminiFunctions.toFunctionDeclarationDto()))
        )

    companion object {
        const val USER = "user"
        const val MODEL = "model"
    }
}
