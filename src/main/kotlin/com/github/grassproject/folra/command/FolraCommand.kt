package com.github.grassproject.folra.command

import com.github.grassproject.folra.command.argument.CustomArgument
import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import org.bukkit.permissions.Permission

class FolraCommand {
    private var name: String
    private var description: String
    private var permission: Permission
    private var alias: List<String>

    private var required:(CommandSourceStack)-> Boolean = { true }

    private val commands: LiteralArgumentBuilder<CommandSourceStack>

    constructor(name: String, description: String, permission: String, alias: List<String>) {
        this.name= name
        this.description= description
        this.permission= Permission(permission)
        this.alias= alias

        commands= Commands.literal(this.name).apply {
            requires { sender ->
                sender.sender.hasPermission(this@FolraCommand.permission)
                        && required(sender)
            }
        }
    }
    constructor(name: String, description: String, permission: Permission, alias: List<String>) {
        this.name= name
        this.description= description
        this.permission= permission
        this.alias= alias

        commands= Commands.literal(this.name).apply {
            requires { sender ->
                sender.sender.hasPermission(this@FolraCommand.permission)
                        && required(sender)
            }
        }
    }

    fun addBranch(
        branch: String,
        executor: (CommandContext<CommandSourceStack>) -> Unit
    ): FolraCommand {
        val node = Commands.literal(branch)
            .executes { ctx ->
                executor(ctx)
                Command.SINGLE_SUCCESS
            }

        commands.then(node)
        return this
    }


    fun <T : Enum<T>> addBranch(
        branch: String,
        argument: String,
        suggestion: CustomArgument<T>,
        executor: (CommandContext<CommandSourceStack>) -> Unit
    ): FolraCommand {
        val node = Commands.literal(branch)
            .then(Commands.argument(argument, suggestion)
                .executes { ctx ->
                    executor(ctx)
                    Command.SINGLE_SUCCESS
                })

        commands.then(node)
        return this
    }


    fun setRequire(require:(CommandSourceStack)-> Boolean) = apply {
        this.required= require
    }

    fun get(): LiteralArgumentBuilder<CommandSourceStack> {
        return commands
    }

    fun build(): LiteralCommandNode<CommandSourceStack> {
        return commands.build()
    }
}