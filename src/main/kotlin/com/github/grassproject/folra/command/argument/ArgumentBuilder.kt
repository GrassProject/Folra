package com.github.grassproject.folra.command.argument

import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.exceptions.CommandSyntaxException
import io.papermc.paper.command.brigadier.argument.CustomArgumentType

open class ArgumentBuilder {
    inline fun <reified T : Enum<T>> build(): CustomArgument<T> {
        return CustomArgument(T::class.java.enumConstants.associateBy { it.name.lowercase() })
    }
}

open class CustomArgument<T : Enum<T>>(
    private val validEnums: Map<String, T>
) : CustomArgumentType.Converted<T, String> {
    override fun getNativeType(): ArgumentType<String> {
        return StringArgumentType.word()
    }

    override fun convert(nativeType: String): T {
        val lowercaseInput = nativeType.lowercase()

        return validEnums[lowercaseInput]
            ?: throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.literalIncorrect()
                .createWithContext(StringReader(nativeType), nativeType)
    }

    override fun getExamples(): Collection<String> {
        return validEnums.keys.toList()
    }
}