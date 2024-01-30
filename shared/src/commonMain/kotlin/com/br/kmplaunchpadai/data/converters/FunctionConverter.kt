package com.br.kmplaunchpadai.data.converters

import com.br.kmplaunchpadai.data.model.FunctionDeclarationDto
import com.br.kmplaunchpadai.domain.mediator.GeminiFunction


fun FunctionDeclarationDto.toFunction() = GeminiFunction(
    description = description,
    name = name,
    parameters = parameters.toParameterList()
)