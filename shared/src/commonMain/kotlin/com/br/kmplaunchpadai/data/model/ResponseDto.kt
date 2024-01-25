package com.br.kmplaunchpadai.data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDto(
    @SerialName("content") val content: ContentXDto,
    @SerialName("name") val name: String,
) : Dto

