package com.br.kmplaunchpadai.data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ParametersDto(
    @SerialName("properties") val properties: PropertiesDto,
    @SerialName("required") val required: List<String>,
    @SerialName("type") val type: String,
) : Dto

@Serializable
data class UsageMetadataDto(
    @SerialName("candidatesTokenCount") val candidatesTokenCount: Int,
    @SerialName("promptTokenCount") val promptTokenCount: Int,
    @SerialName("totalTokenCount") val totalTokenCount: Int
) : Dto



