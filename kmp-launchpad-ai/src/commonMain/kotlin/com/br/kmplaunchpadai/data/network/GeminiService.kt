package com.br.kmplaunchpadai.data.network

import com.br.kmplaunchpadai.data.model.ConversationRequestDto
import com.br.kmplaunchpadai.data.model.ConversationResponseDto
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class GeminiService(private var apiKey: String = "KEY_NOT_INITIALIZED") {
    private val client = ktorClient()
    private val url get() = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=$apiKey"

    suspend fun callGemini(payload: ConversationRequestDto): Result<ConversationResponseDto> =
        runCatching {
            client.post(url) {
                contentType(ContentType.Application.Json)
                setBody(payload)
            }.body<ConversationResponseDto>()
        }
}
