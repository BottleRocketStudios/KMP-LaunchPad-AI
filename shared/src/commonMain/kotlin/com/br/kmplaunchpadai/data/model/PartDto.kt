package com.br.kmplaunchpadai.data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PartDto(
    @SerialName("functionCall") val functionCall: FunctionCallDto?,
    @SerialName("functionResponse") val functionResponse: FunctionResponseDto?,
    @SerialName("text") val text: String?,
) : Dto