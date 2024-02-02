package com.br.kmplaunchpadai.data.model
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDto(
    @SerialName("content") val content: Map<String, @Contextual Any>,
    @SerialName("name") val name: String,
) : Dto

