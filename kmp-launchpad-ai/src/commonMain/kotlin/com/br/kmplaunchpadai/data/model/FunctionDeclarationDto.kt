package com.br.kmplaunchpadai.data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FunctionDeclarationDto(
    @SerialName("description") val description: String,
    @SerialName("name") val name: String,
    @SerialName("parameters") val parameters: ParametersDto
) : Dto
