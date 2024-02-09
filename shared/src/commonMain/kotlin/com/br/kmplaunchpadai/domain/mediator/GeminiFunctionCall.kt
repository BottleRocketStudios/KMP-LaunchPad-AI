package com.br.kmplaunchpadai.domain.mediator

data class GeminiFunctionCall(
    val name: String,
    val args: Map<String, String>,
)