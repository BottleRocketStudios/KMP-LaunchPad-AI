package com.br.kmplaunchpadai.domain.mediator

import com.br.kmplaunchpadai.data.converters.toDto
import com.br.kmplaunchpadai.data.converters.toFunctionDeclarationDto
import com.br.kmplaunchpadai.data.model.ConversationRequestDto
import com.br.kmplaunchpadai.data.model.ToolDto
import com.br.kmplaunchpadai.data.network.GeminiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class GeminiMediator(scope: CoroutineScope, apiKey: String) {
    // Service
    private val geminiService = GeminiService(apiKey)

    // State
    private val geminiFunctions: MutableList<GeminiFunction> = mutableListOf()
    private val mutex = Mutex()
    private val _conversationFlow = MutableStateFlow(
        GeminiContent(STATUS, GeminiPart(text = "Start of Conversation"))
    )

    // Input flow
    val user: MutableSharedFlow<String> =  MutableSharedFlow()

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
        coroutineScope {
            launch {
                user.collect { userInput ->
                    val content = GeminiContent(USER, GeminiPart(text = userInput))
                    _conversationFlow.emit(content)
                }
            }
            launch {
                conversation.collect{
//                     TODO - Add function response type to list when it is created
                    if (it.lastOrNull()?.role in listOf(USER)) callGemini()
                }
            }
        }
    }

    private suspend fun callGemini() {
//        TODO - Add escape logic to prevent infinite loop

        geminiService.callGemini(createConversationRequestDto()).onSuccess { response ->

            response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text?.let {
                val element = GeminiContent(
                    role = MODEL,
                    part = GeminiPart(text = it)
                )
                _conversationFlow.value = element
            }

            response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.functionCall?.let { functionCall ->
                _conversationFlow.value = GeminiContent(
                    role = MODEL,
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
                    role = MODEL,
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
            _conversationFlow.value = GeminiContent(
                role = STATUS,
                part = GeminiPart(text = "Error: ${it.message}")
            )
        }
    }

    private fun createConversationRequestDto() =
        ConversationRequestDto(
            // Send everything but STATUS messages to server
            conversation.value.filter { it.role != STATUS }.toDto(),
            listOf(ToolDto(geminiFunctions.toFunctionDeclarationDto()))
        )

    companion object {
        const val USER = "user"
        const val MODEL = "model"
        const val STATUS = "status"
    }
}
