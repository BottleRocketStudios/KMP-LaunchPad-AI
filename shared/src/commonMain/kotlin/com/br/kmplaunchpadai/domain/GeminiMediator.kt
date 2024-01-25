package com.br.kmplaunchpadai.domain

import com.br.kmplaunchpadai.domain.model.GeminiFunction

class GeminiMediator {

    private val tools: MutableList<GeminiFunction> = mutableListOf()

    operator fun invoke(init: GeminiMediator.() -> Unit)  {
        init()
    }
//     TODO create DSL to populating tools

//    MOVE TOOLS into its own class
    fun tools(init: GeminiMediator.() -> Unit) {
        init()
    }


    fun functionDeclaration(init: GeminiFunction.() -> Unit) {
        val gFunc = GeminiFunction()
        GeminiFunction().init()
        tools.add(gFunc)
    }
}

fun test() {
    val geminiMediator = GeminiMediator()
    geminiMediator {
        tools {
            functionDeclaration {
                description { "BLA" }
                name { "Name" }
            }
        }
    }

    geminiMediator.chat("hey bot whats up.")
    geminiMediator.response

}