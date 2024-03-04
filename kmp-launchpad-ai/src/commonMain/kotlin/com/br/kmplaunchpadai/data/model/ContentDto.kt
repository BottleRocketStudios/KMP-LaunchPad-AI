package com.br.kmplaunchpadai.data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContentDto(
    @SerialName("parts") val parts: List<PartDto>,
    @SerialName("role") val role: String?
) : Dto
