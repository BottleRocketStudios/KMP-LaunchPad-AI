package com.br.kmplaunchpadai.domain.mediator

class GeminiParameter(
    val name: String,
    val description: String,
    val required: Boolean,
    var value: Any? = null,
)

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
