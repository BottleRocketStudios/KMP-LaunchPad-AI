package com.br.kmplaunchpadai.domain.mediator

import kotlinx.coroutines.flow.MutableStateFlow


class GeminiMediator {
    // State
    private val conversation: MutableList<GeminiContent> = mutableListOf()
    private val tools: MutableList<GeminiFunction> = mutableListOf()

    companion object {
        private const val USER = "user"
        private const val MODEL = "model"
    }

    // input flow
    val user: MutableStateFlow<String> = MutableStateFlow("")

    // output
    //    TODO -use _ to make it private
    val conversationFlow = MutableStateFlow(GeminiContent(MODEL, GeminiPart(text = "Welcome to das BOT:")))

    // Methods
    operator fun invoke(init: GeminiMediator.() -> Unit)  {
        init()
    }

    fun tools(init: GeminiMediator.() -> Unit) {
        init()
    }

// TODO - Tool scope
    fun functionDeclaration(init: GeminiFunction.() -> Unit) {
        val gFunc = GeminiFunction()
        GeminiFunction().init()
        tools.add(gFunc)
    }

    suspend fun startChat() {
        user.collect {
            val content = GeminiContent(USER, GeminiPart(text = it))
            conversation.add(content)
            conversationFlow.emit(content)
            //  TODO - call Gemini model.
        }

    }


}
