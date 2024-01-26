package com.br.kmplaunchpadai.domain.model

import kotlin.reflect.KFunction1

class GeminiFunction {
    private var description: String? = null
    private var name: String? = null
    private var parameters: List<GeminiParameter> = emptyList()
    private lateinit var functionReference: KFunction1<Map<String, Any>, Any?>

    fun description(init: () -> String) {
        description = init()
    }
    // Function to set name
    fun name(init: () -> String) {
        name = init()
    }

    fun parameters(init: () -> Unit) {
        init()
    }

    fun parameter(init: ParameterScope.() -> Unit) {
        val scope = ParameterScope()
        scope.init()
        parameters = parameters + scope.getParameter()
    }

    fun functionReference(init: () -> KFunction1<Map<String, Any>, Any?>) {
        functionReference = init()
    }

//    TODO - write function to call function reference
    fun call() {

    }
}

class ParameterScope {
    private var name = ""
    private var description = ""
    private var required: Boolean = false

    fun name(init: () -> String) {
        name = init()
    }

    fun description(init: () -> String) {
        description = init()
    }

    fun required(init: () -> Boolean) {
        required = init()
    }

    fun getParameter() = GeminiParameter(name, description, required)
}




