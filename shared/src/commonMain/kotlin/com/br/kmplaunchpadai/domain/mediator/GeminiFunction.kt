package com.br.kmplaunchpadai.domain.mediator

import kotlin.reflect.KFunction1

/**
 * Represents a Gemini function.
 */
class GeminiFunction {
    var description: String? = null
    var name: String? = null
    var parameters: List<GeminiParameter> = emptyList()
    private lateinit var functionReference: KFunction1<Map<String, Any>, Any?>

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
    fun functionReference(init: () -> KFunction1<Map<String, Any>, Any?>) {
        functionReference = init()
    }

    /**
     * Calls the function.
     *
     * @return The result of the function call.
     */
    fun call(): Any? {
        // Create a mutable map to store the parameters.
        val params = mutableMapOf<String, Any>()

        // Iterate over the parameters and add them to the map.
        parameters.forEach { parameter ->
            // If the parameter has a value, add it to the map.
            parameter.value?.let {
                params[parameter.name] = it
            }
            // If the parameter is required and does not have a value, throw an exception.
                ?: if (parameter.required) {
                    throw IllegalArgumentException("Missing Param ${parameter.name}")
                } else null;
        }

        // Call the function and return the result.
        return functionReference.invoke(params)
    }
}
