package com.br.kmplaunchpadai.data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TheaterXDto(
    @SerialName("description") val description: String,
    @SerialName("type") val type: String
)
