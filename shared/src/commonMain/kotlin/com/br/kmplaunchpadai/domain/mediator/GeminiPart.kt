package com.br.kmplaunchpadai.domain.mediator

class GeminiPart(
    var functionCall: GeminiFunction? = null,
    var text: String? = null,
    var functionResponse: GeminiResponse? = null
)