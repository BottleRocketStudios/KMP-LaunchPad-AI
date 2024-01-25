package com.br.kmplaunchpadai.domain.model

import com.br.kmplaunchpadai.data.model.ParametersDto

data class GeminiFunction(
    private var description: String? = null,
    private var name: String? = null,
    val parameters: List<GeminiParameter> = emptyList()
//    TODO - add function reference
) {
    fun description(init: () -> String) {
        description = init()
    }
    // Function to set name
    fun name(init: () -> String) {
        name = init()
    }

}




