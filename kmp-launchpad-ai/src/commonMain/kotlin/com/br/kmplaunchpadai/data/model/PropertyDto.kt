package com.br.kmplaunchpadai.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PropertyDto(
    @SerialName("type") val type: String = "STRING",
    @SerialName("description") val description: String = ""
)
