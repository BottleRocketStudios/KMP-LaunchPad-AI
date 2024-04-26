package com.br.kmplaunchpadai.data.model
import com.br.kmplaunchpadai.domain.mediator.GeminiResponseType
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

@Serializable
data class ResponseDto(
    @SerialName("content") val content: GeminiResponseType?,
    @SerialName("name") val name: String
) : Dto
