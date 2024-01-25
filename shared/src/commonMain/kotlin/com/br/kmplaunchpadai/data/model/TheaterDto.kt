package com.br.kmplaunchpadai.data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TheaterDto(
    @SerialName("address") val address: String,
    @SerialName("name") val name: String,
) : Dto