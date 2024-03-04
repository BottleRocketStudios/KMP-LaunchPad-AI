package com.br.kmplaunchpadai.domain.mediator

class GeminiPart(
    var functionCall: GeminiFunctionCall? = null,
    var text: String? = null,
    var functionResponse: GeminiResponse? = null
)
