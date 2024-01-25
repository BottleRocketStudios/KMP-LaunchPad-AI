package com.br.kmplaunchpadai.data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ToolDto(
    @SerialName("functionDeclarations") val functionDeclarations: List<FunctionDeclarationDto>
) : Dto