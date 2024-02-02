package com.br.kmplaunchpadai.data.network

import com.br.kmplaunchpadai.data.model.ConversationRequestDto
import com.br.kmplaunchpadai.data.model.ConversationResponseDto

class GeminiService {
    private val client = ktorClient()
//     TODO - write service.

    suspend fun callGemini(payload: ConversationRequestDto): Result<ConversationResponseDto>{
        return try {
            val response = client.post<ConversationResponseDto>
    }
}