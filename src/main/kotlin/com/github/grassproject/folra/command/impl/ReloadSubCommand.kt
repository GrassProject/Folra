package com.github.grassproject.folra.command.impl

import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.command.IFolraSubCommand
import com.github.grassproject.folra.language.FolraTranslate
import org.bukkit.command.CommandSender

abstract class ReloadSubCommand(
    val plugin: FolraPlugin
) : IFolraSubCommand {
    override val permission: String = "command.${plugin.name}.reload"

    override fun execute(sender: CommandSender, args: Array<out String>) {
        plugin.saveDefaultConfig()
        plugin.reloadConfig()
        FolraTranslate(plugin)

        onReload(sender, args)
    }

    protected abstract fun onReload(sender: CommandSender, args: Array<out String>)
}