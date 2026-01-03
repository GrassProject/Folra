package com.github.grassproject.folra.hangul

import com.github.grassproject.folra.command.argument.CustomArgument
import org.jspecify.annotations.NullMarked
import java.util.*

@NullMarked
class HangulArgument : CustomArgument<HangulArgument.HangulDefault>(
    HangulDefault.entries.associateBy { it.name.lowercase() }
) {
    init {

    }

    @NullMarked
    enum class HangulDefault {
        옵션,

        ;
        override fun toString(): String {
            return name.lowercase(Locale.getDefault())
        }
    }
}