package com.br.kmplaunchpadai.data.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PropertiesDto(
    @SerialName("date") val date: DateDto,
    @SerialName("description") val description: DescriptionDto,
    @SerialName("location") val location: LocationDto,
    @SerialName("movie") val movie: MovieDto,
    @SerialName("theater") val theater: TheaterXDto
) : Dto