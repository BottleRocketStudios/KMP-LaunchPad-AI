package com.br.kmplaunchpadai.domain.mediator

import com.br.kmplaunchpadai.data.converters.toDto
import com.br.kmplaunchpadai.data.model.ConversationRequestDto
import com.br.kmplaunchpadai.data.model.FunctionCallDto
import com.br.kmplaunchpadai.data.model.ToolDto
import com.br.kmplaunchpadai.data.network.GeminiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GeminiMediator {
    // Service
    private val geminiService = GeminiService()

    // State
    private val conversation: MutableList<GeminiContent> = mutableListOf()
    private val geminiFunctions: MutableList<GeminiFunction> = mutableListOf()

    // Input flow
    val user: MutableStateFlow<String> = MutableStateFlow("")

    // Output
    private val _conversationFlow = MutableStateFlow(GeminiContent(MODEL, GeminiPart(text = "Welcome to das BOT:")))
    val conversationFlow: StateFlow<GeminiContent> = _conversationFlow

    // Methods
    operator fun invoke(init: GeminiMediator.() -> Unit)  {
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
            geminiService.callGemini(createConversationRequestDto()).onSuccess { response ->

//                FIXME - What if there is no function call ???  Process text first and output to conversation and UI

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
                    TODO("process API response and add to conversation")
                    TODO("Call Gemini again")
                    TODO("process Gemini response and add to conversation")
                }

            }.onFailure {
                TODO("Not yet implemented")
            }
        }
    }


    private fun createConversationRequestDto() =
        ConversationRequestDto(conversation.toDto(), listOf(ToolDto( geminiFunctions.toDto())))


    companion object {
        private const val USER = "user"
        private const val MODEL = "model"
    }

}




