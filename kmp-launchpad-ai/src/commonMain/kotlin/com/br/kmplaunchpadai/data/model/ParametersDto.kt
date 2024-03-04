package com.br.kmplaunchpadai.data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ParametersDto(
    @SerialName("type") val type: String = "OBJECT",
    @SerialName("properties") val properties: Map<String, PropertyDto>,
    @SerialName("required") val required: List<String>
) : Dto
