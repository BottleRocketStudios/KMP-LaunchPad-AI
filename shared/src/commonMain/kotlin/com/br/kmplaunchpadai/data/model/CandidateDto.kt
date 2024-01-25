package com.br.kmplaunchpadai.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CandidateDto(
    @SerialName("content") val content: ContentDto
) : Dto