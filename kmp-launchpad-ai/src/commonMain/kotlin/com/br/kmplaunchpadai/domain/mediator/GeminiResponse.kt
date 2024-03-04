package com.br.kmplaunchpadai.domain.mediator

data class GeminiResponse(
    var name: String? = null,
    var content: Map<String, Any>? = null
)
