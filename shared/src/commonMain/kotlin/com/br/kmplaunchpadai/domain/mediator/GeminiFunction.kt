package com.br.kmplaunchpadai.domain.mediator

import kotlin.reflect.KFunction1

/**
 * Represents a Gemini function.
 */
class GeminiFunction {
    var description: String = ""
    var name: String = ""
    var parameters: List<GeminiParameter> = emptyList()
    private lateinit var functionReference: KFunction1<Map<String, Any>, Map<String, Any>>

    /**
     * Sets the name of the function.
     *
     * @param init A lambda that returns the name of the function.
     */
    fun name(init: () -> String) {
        name = init()
    }

    /**
     * Sets the description of the function.
     *
     * @param init A lambda that returns the description of the function.
     */
    fun description(init: () -> String) {
        description = init()
    }

    /**
     * Adds a parameter to the function.
     *
     * @param init A lambda that initializes the parameter.
     * @return The created parameter.
     */
    fun parameter(init: GeminiParameter.() -> Unit): GeminiParameter {
        val param = GeminiParameter()
        param.init()
        parameters = parameters + param
        return param
    }

    /**
     * Sets the function reference.
     *
     * @param init A lambda that returns the function reference.
     */
    fun functionReference(init: () -> KFunction1<Map<String, Any>, Map<String, Any>>) {
        functionReference = init()
    }

    /**
     * Calls the function.
     *
     * @return The result of the function call.
     */
    fun call(params: Map<String, Any>) = functionReference.invoke(params)
}
