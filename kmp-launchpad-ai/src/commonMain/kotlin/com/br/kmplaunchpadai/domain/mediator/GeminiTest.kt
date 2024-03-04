package com.br.kmplaunchpadai.domain.mediator

import com.br.kmplaunchpadai.domain.ExampleFunctions

suspend fun test() {
    val geminiMediator = GeminiMediator()
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
    geminiMediator.user.value = "Hi. Are you there???"

//    geminiMediator.chat("hey bot whats up.")
//    geminiMediator.response
}
