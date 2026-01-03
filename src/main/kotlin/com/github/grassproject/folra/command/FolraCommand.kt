package com.github.grassproject.folra.command

import com.github.grassproject.folra.command.argument.CustomArgument
import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import org.bukkit.permissions.Permission

typealias Executor = (CommandContext<CommandSourceStack>) -> Unit
typealias Requirement = (CommandSourceStack) -> Boolean

class FolraCommand private constructor(
    private val spec: Spec
) {

    data class Spec(
        val name: String,
        val description: String = "",
        val permission: Permission? = null,
        val aliases: List<String> = emptyList(),
        val requirement: Requirement = { true }
    )

    private val root: LiteralArgumentBuilder<CommandSourceStack> =
        Commands.literal(spec.name).requires { source ->
            (spec.permission?.let { source.sender.hasPermission(it) } ?: true)
                    && spec.requirement(source)
        }

    companion object {
        fun create(
            name: String,
            description: String = ""
        ): Builder = Builder(name, description)
    }

    class Builder(
        private val name: String,
        private val description: String
    ) {
        private var permission: Permission? = null
        private var aliases: List<String> = emptyList()
        private var requirement: Requirement = { true }

        fun permission(permission: Permission) = apply {
            this.permission = permission
        }

        fun permission(permission: String) = apply {
            this.permission = Permission(permission)
        }

        fun aliases(vararg alias: String) = apply {
            this.aliases = alias.toList()
        }

        fun requires(requirement: Requirement) = apply {
            this.requirement = requirement
        }

        fun build(): FolraCommand {
            return FolraCommand(
                Spec(
                    name = name,
                    description = description,
                    permission = permission,
                    aliases = aliases,
                    requirement = requirement
                )
            )
        }
    }

    fun executes(executor: Executor) = apply {
        root.executes {
            executor(it)
            Command.SINGLE_SUCCESS
        }
    }

    fun literal(
        name: String,
        block: FolraCommand.() -> Unit = {},
        executor: Executor? = null
    ) = apply {
        val child = Commands.literal(name)
        executor?.let { exec ->
            child.executes {
                exec(it)
                Command.SINGLE_SUCCESS
            }
        }
        val nested = FolraCommand(
            spec.copy(name = name)
        )

        nested.root.then(child)
        nested.block()

        root.then(child)
    }

    fun <T : Enum<T>> argument(
        name: String,
        argument: CustomArgument<T>,
        executor: Executor
    ) = apply {
        root.then(
            Commands.argument(name, argument.nativeType)
                .executes { ctx ->
                    val value = argument.convert(
                        StringArgumentType.getString(ctx, name)
                    )

                    executor(ctx)
                    Command.SINGLE_SUCCESS
                }
        )
    }

    fun then(builder: ArgumentBuilder<CommandSourceStack, *>) = apply {
        root.then(builder)
    }

    fun getBuilder(): LiteralArgumentBuilder<CommandSourceStack> = root

    fun build(): LiteralCommandNode<CommandSourceStack> = root.build()
}
