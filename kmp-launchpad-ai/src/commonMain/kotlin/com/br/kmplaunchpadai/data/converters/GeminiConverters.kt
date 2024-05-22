package com.br.kmplaunchpadai.data.converters

import com.br.kmplaunchpadai.data.model.ContentDto
import com.br.kmplaunchpadai.data.model.FunctionCallDto
import com.br.kmplaunchpadai.data.model.FunctionDeclarationDto
import com.br.kmplaunchpadai.data.model.FunctionResponseDto
import com.br.kmplaunchpadai.data.model.ParametersDto
import com.br.kmplaunchpadai.data.model.PartDto
import com.br.kmplaunchpadai.data.model.PropertyDto
import com.br.kmplaunchpadai.data.model.ResponseDto
import com.br.kmplaunchpadai.domain.mediator.GeminiContent
import com.br.kmplaunchpadai.domain.mediator.GeminiFunction
import com.br.kmplaunchpadai.domain.mediator.GeminiFunctionCall
import com.br.kmplaunchpadai.domain.mediator.GeminiParameter
import com.br.kmplaunchpadai.domain.mediator.GeminiPart
import com.br.kmplaunchpadai.domain.mediator.GeminiResponse

fun List<GeminiContent>.toDto() = map { it.toDto() }

fun GeminiContent.toDto() = ContentDto(
    role = role ?: "",
    parts = part?.let { listOf(it.toDto()) } ?: emptyList()
)

fun GeminiPart.toDto() = PartDto(
    text = text,
    functionCall = functionCall?.functionCallDto(),
    functionResponse = functionResponse?.toDto()
)

 fun GeminiFunctionCall.functionCallDto() = FunctionCallDto(
    name = name,
    args = args
 )

fun GeminiResponse.toDto() = FunctionResponseDto(
    name = name ?: "",
    response = ResponseDto(
        name = name ?: "",
        content = content
    )
)

fun List<GeminiFunction>.toFunctionDeclarationDto() = map { it.toDto() }

fun GeminiFunction.toDto() = FunctionDeclarationDto(
    description = description,
    name = name,
    parameters = genParams()
)

fun GeminiFunction.genParams() = ParametersDto(
    properties = parameters.associate { it.name to it.toDto() },
    required = parameters.filter { it.required }.map { it.name }
)

fun GeminiParameter.toDto() = PropertyDto(description = description)
