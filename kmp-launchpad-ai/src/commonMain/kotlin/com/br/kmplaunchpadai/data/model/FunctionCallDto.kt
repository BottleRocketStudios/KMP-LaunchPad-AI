package com.br.kmplaunchpadai.data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FunctionCallDto(
    @SerialName("name") val name: String,
    @SerialName("args") val args: Map<String, String>,
) : Dto
