package com.br.kmplaunchpadai.domain.mediator

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

    fun call(): Any? {
        val params = mutableMapOf<String, Any>()
        parameters.forEach { parameter ->
            parameter.value?.let {
                params[parameter.name] = it
            } ?: if (parameter.required) { throw IllegalArgumentException("Missing Param ${parameter.name}") } else null;
        }
        return functionReference.invoke(params)
    }
}

// TODO - Do we need function scope ??
class FunctionScope() {

}







