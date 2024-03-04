package com.br.kmplaunchpadai.data.network

import com.br.kmplaunchpadai.data.model.ConversationRequestDto
import com.br.kmplaunchpadai.data.model.ConversationResponseDto
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class GeminiService {
    private val client = ktorClient()
    companion object {
//        FIXME - USe Build config to drive API key
        private const val API_KEY: String = "DUNNO_YET"
        private const val URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=$API_KEY"
    }

    suspend fun callGemini(payload: ConversationRequestDto): Result<ConversationResponseDto> =
        runCatching {
            client.post(URL) {
                contentType(ContentType.Application.Json)
                setBody(payload)
            }.body<ConversationResponseDto>()
        }
}
