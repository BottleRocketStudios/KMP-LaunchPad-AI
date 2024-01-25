package com.br.kmplaunchpadai.data.network

import com.bottlerocketstudios.ignitedomain.domain.model.ApiException
import com.bottlerocketstudios.kmpignite.data.BuildKonfig
import com.br.kmplaunchpadai.domain.model.ApiException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.http.URLProtocol
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.appendIfNameAbsent
import kotlinx.serialization.json.Json

fun ktorClient(): HttpClient = HttpClient {
    expectSuccess = true

    install(Logging) {
        level = LogLevel.ALL
        logger = object : Logger {
            override fun log(message: String) {
                println(message)
            }
        }
    }

    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                encodeDefaults = true
                isLenient = true
            }
        )
    }

    HttpResponseValidator {
        validateResponse { response ->
            if (!response.status.isSuccess()) {
                val error: Error = response.body()
                throw ApiException(
                    code = response.status.value,
                    message = error.message.toString(),
                    subCode = 0,
                    subMessage = "",
                    errorNumber = 0
                )
            }
        }
    }

    defaultRequest {
        url {
            protocol = URLProtocol.HTTPS
        }
    }
}
