package com.br.kmplaunchpadai.data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// FIXME - Break into separate files
@Serializable
data class ParametersDto(
    @SerialName("properties") val properties: Map<String, PropertyDto>,
    @SerialName("required") val required: List<String>,
    @SerialName("type") val type: String,
) : Dto

@Serializable
data class PropertyDto (
    @SerialName("type") val type: String = "STRING",
    @SerialName("description") val description: String = ""
)

@Serializable
data class UsageMetadataDto(
    @SerialName("candidatesTokenCount") val candidatesTokenCount: Int,
    @SerialName("promptTokenCount") val promptTokenCount: Int,
    @SerialName("totalTokenCount") val totalTokenCount: Int
) : Dto



