package com.br.kmplaunchpadai.domain.mediator

import kotlinx.serialization.json.JsonElement
import kotlin.reflect.KFunction1


typealias GeminiParametersType = Map<String, String>
typealias GeminiResponseType = JsonElement
typealias GeminiFunctionType = KFunction1<GeminiParametersType, GeminiResponseType?>

/**
 * Represents a Gemini function.
 */
class GeminiFunction {
    var description: String = ""
    var name: String = ""
    var parameters: List<GeminiParameter> = emptyList()
    private lateinit var functionReference: GeminiFunctionType

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
    fun functionReference(init: () -> GeminiFunctionType) {
        functionReference = init()
    }

    /**
     * Calls the function.
     *
     * @return The result of the function call.
     */
    fun call(params: GeminiParametersType) = functionReference.invoke(params)
}
