package com.br.kmplaunchpadai.data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContentXDto(
    @SerialName("movie") val movie: String,
    @SerialName("theaters") val theaters: List<TheaterDto>,
) : Dto