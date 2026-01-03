package com.github.grassproject.folra.util

@Deprecated("Using net.kyori.adventure.key.Key")
data class Key(
    val namespace: String,
    val value: String
) {

    fun decompose(): Array<String> =
        arrayOf(namespace, value)

    fun asString(): String =
        "$namespace:$value"

    fun asMinimalString(): String =
        if (namespace == "minecraft") value else asString()

    override fun toString(): String = asString()

    companion object {
        const val DEFAULT_NAMESPACE = "frixelengine"

        fun withDefaultNamespace(value: String): Key =
            Key(DEFAULT_NAMESPACE, value)

        fun of(namespace: String, value: String): Key =
            Key(namespace, value)

        fun withDefaultNamespace(namespacedId: String, defaultNamespace: String): Key =
            of(decompose(namespacedId, defaultNamespace))

        fun of(id: Array<String>): Key =
            Key(id[0], id[1])

        fun of(namespacedId: String): Key =
            of(decompose(namespacedId, "minecraft"))

        fun from(namespacedId: String): Key =
            of(decompose(namespacedId, "minecraft"))

        fun fromNamespaceAndPath(namespace: String, path: String): Key =
            of(namespace, path)

        private fun decompose(id: String, fallbackNamespace: String): Array<String> {
            val index = id.indexOf(':')
            return if (index >= 0) {
                val ns = id.substring(0, index).ifEmpty { fallbackNamespace }
                val value = id.substring(index + 1)
                arrayOf(ns, value)
            } else {
                arrayOf(fallbackNamespace, id)
            }
        }
    }
}
