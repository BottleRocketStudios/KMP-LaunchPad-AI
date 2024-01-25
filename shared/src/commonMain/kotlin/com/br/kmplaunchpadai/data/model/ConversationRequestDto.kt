package com.br.kmplaunchpadai.data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConversationRequestDto(
    @SerialName("contents") val contents: List<ContentDto>,
    @SerialName("tools") val tools: List<ToolDto>,
) : Dto