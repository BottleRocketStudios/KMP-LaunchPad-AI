package com.br.kmplaunchpadai.data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FunctionResponseDto(
    @SerialName("name") val name: String,
    @SerialName("response") val response: ResponseDto
) : Dto
