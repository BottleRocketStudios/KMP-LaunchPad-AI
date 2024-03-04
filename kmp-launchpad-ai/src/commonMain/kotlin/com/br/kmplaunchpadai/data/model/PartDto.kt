package com.br.kmplaunchpadai.data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PartDto(
    @SerialName("functionCall") val functionCall: FunctionCallDto? = null,
    @SerialName("functionResponse") val functionResponse: FunctionResponseDto? = null,
    @SerialName("text") val text: String? = null
) : Dto
