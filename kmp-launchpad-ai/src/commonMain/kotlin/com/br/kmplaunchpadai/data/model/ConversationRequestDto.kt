package com.br.kmplaunchpadai.data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


// Schema : https://ai.google.dev/api/rest/v1beta/models/generateContent
// TODO - add System instruction here for persona
@Serializable
data class ConversationRequestDto(
    @SerialName("contents") val contents: List<ContentDto>,
    @SerialName("tools") val tools: List<ToolDto>
) : Dto
