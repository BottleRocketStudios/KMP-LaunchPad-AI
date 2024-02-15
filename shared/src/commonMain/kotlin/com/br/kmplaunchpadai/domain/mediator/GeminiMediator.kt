package com.br.kmplaunchpadai.domain.mediator

import com.br.kmplaunchpadai.data.converters.toDto
import com.br.kmplaunchpadai.data.model.ConversationRequestDto
import com.br.kmplaunchpadai.data.model.FunctionCallDto
import com.br.kmplaunchpadai.data.model.ToolDto
import com.br.kmplaunchpadai.data.network.GeminiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class GeminiMediator {
    // Service
    private val geminiService = GeminiService()

    // State
    private val conversation: MutableList<GeminiContent> = mutableListOf()
    private val geminiFunctions: MutableList<GeminiFunction> = mutableListOf()
    private val mutex = Mutex()

    // Input flow
    val user: MutableStateFlow<String> = MutableStateFlow("")

    // Output
    private val _conversationFlow = MutableStateFlow(GeminiContent(MODEL, GeminiPart(text = "Welcome to das BOT:")))
    val conversationFlow: StateFlow<GeminiContent> = _conversationFlow

    private val _errorString: MutableStateFlow<String> = MutableStateFlow("")
    val errorString: StateFlow<String> = _errorString

    // Methods
    operator fun invoke(init: GeminiMediator.() -> Unit) {
        init()
    }

    fun functionDeclaration(init: GeminiFunction.() -> Unit) {
        val gFunc = GeminiFunction()
        GeminiFunction().init()
        geminiFunctions.add(gFunc)
    }

    suspend fun startChat() {
        user.collect { userInput ->
            val content = GeminiContent(USER, GeminiPart(text = userInput))
            conversation.add(content)
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
                conversation.add(element)
                _conversationFlow.value = element
            }

            response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.functionCall?.let { functionCall ->
                conversation.add(
                    GeminiContent(
                        role = "model",
                        part = GeminiPart(
                            functionCall = GeminiFunctionCall(
                                name = functionCall.name,
                                args = functionCall.args
                            )
                        )
                    )
                )

                val functionResponse = geminiFunctions.find { it.name == functionCall.name }?.call(functionCall.args)
                conversation.add(
                    GeminiContent(
                        role = "model",
                        part = GeminiPart(
                            functionResponse = GeminiResponse(
                                name = functionCall.name,
                                content = functionResponse
                            )
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
        ConversationRequestDto(conversation.toDto(), listOf(ToolDto(geminiFunctions.toDto())))


    companion object {
        private const val USER = "user"
        private const val MODEL = "model"
    }

}
