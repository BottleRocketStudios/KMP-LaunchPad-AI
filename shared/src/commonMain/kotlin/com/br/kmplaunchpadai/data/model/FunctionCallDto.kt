package com.br.kmplaunchpadai.data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FunctionCallDto(
    @SerialName("args") val args: ArgsDto,
    @SerialName("name") val name: String,
) : Dto