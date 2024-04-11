package com.br.kmplaunchpadai.domain.mediator

import com.br.kmplaunchpadai.domain.ExampleFunctions
import kotlinx.coroutines.MainScope

suspend fun test() {
    val geminiMediator = GeminiMediator(MainScope(), "KEY")
    geminiMediator {
        functionDeclaration {
            description { "BLA" }
            name { "Name" }
            parameter {
                name { "name" }
                description { "String" }
                required { true }
                functionReference { ExampleFunctions::findMovies }
            }
        }
    }

    geminiMediator.startChat()
    geminiMediator.user.emit("Hi. Are you there???")

//    geminiMediator.chat("hey bot whats up.")
//    geminiMediator.response
}
