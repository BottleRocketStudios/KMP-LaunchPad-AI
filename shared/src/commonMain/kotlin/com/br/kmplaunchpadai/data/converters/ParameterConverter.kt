package com.br.kmplaunchpadai.data.converters

import com.br.kmplaunchpadai.data.model.ParametersDto
import com.br.kmplaunchpadai.domain.mediator.GeminiParameter

fun ParametersDto.toParameterList(): List<GeminiParameter> {
    val dto = this
    return properties.map { (name, value) ->
        GeminiParameter().apply {
            this.name = name
            this.description = value.description
            this.required = name in dto.required
        }
    }
}