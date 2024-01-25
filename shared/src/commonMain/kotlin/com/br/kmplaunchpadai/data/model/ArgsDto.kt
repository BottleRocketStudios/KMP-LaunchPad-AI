package com.br.kmplaunchpadai.data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArgsDto(
    @SerialName("location") val location: String,
    @SerialName("movie") val movie: String
) : Dto