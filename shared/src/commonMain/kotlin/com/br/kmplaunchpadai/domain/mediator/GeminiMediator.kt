package com.br.kmplaunchpadai.domain.mediator

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class GeminiMediator {
    // State
    private val conversation: MutableList<GeminiContent> = mutableListOf()
    private val functions: MutableList<GeminiFunction> = mutableListOf()

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
        functions.add(gFunc)
    }

    suspend fun startChat() {
        user.collect {
            val content = GeminiContent(USER, GeminiPart(text = it))
            conversation.add(content)
            _conversationFlow.emit(content)
            //  TODO - call Gemini model.
            callGemini()
        }

    }

    private fun callGemini() {

    }


    companion object {
        private const val USER = "user"
        private const val MODEL = "model"
    }

}
