package com.br.kmplaunchpadai.domain.model

class ApiException(
    val code: Int,
    message: String,
    val subCode: Int,
    val subMessage: String,
    val errorNumber: Int
) : RuntimeException(message)
