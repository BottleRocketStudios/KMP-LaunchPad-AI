package com.br.kmplaunchpadai.data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FunctionCallDto(
    @SerialName("args") val args: Map<String, String>,
    @SerialName("name") val name: String,
) : Dto