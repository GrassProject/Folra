package com.github.grassproject.folra.test.command

import com.github.grassproject.folra.command.FolraCommand
import com.github.grassproject.folra.test.command.impl.HelloCommand
import com.github.grassproject.folra.util.message.impl.EmptyMessage
import com.github.grassproject.folra.util.toComponent

class FolraCommandImpl : FolraCommand(
    "folra",
    "folra base command",
    mutableListOf(),
    mutableMapOf(
        "hello" to HelloCommand
    ),
    { EmptyMessage() }
)