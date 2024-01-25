package com.br.kmplaunchpadai.data.converters

import com.br.kmplaunchpadai.data.model.ParametersDto
import com.br.kmplaunchpadai.domain.model.GeminiParameter

fun ParametersDto.toParameterList() =
    properties.map { (name, value) ->
        GeminiParameter(
            name= name,
            description = value.description,
            required = name in required,
        )
    }