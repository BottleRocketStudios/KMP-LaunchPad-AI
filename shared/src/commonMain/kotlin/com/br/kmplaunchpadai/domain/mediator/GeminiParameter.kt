package com.br.kmplaunchpadai.domain.mediator

/**
 * Represents a parameter for a Gemini function.
 */
class GeminiParameter {
    var name: String = ""
    var description: String = ""
    var required: Boolean = false
    var value: Any? = null

    /**
     * Sets the name of the parameter.
     *
     * @param init A lambda that returns the name of the parameter.
     */
    fun name(init: () -> String) {
        name = init()
    }

    /**
     * Sets the description of the parameter.
     *
     * @param init A lambda that returns the description of the parameter.
     */
    fun description(init: () -> String) {
        description = init()
    }

    /**
     * Sets whether the parameter is required.
     *
     * @param init A lambda that returns whether the parameter is required.
     */
    fun required(init: () -> Boolean) {
        required = init()
    }

}
