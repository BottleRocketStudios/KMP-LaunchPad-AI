package com.br.kmplaunchpadai.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConversationResponseDto(
    @SerialName("candidates") val candidates: List<CandidateDto>,
    @SerialName("usageMetadata") val usageMetadata: UsageMetadataDto? = null
) : Dto
