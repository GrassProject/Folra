package com.github.grassproject.folra.command.impl

import com.github.grassproject.folra.api.FolraPlugin
import com.github.grassproject.folra.command.IFolraCommand
import com.github.grassproject.folra.language.FolraTranslate
import org.bukkit.command.CommandSender

open class ReloadCommand(
    val plugin: FolraPlugin
) : IFolraCommand {
    override val permission: String = "command.${plugin.name}.reload"

    override fun execute(sender: CommandSender, args: Array<out String>) {
        plugin.saveDefaultConfig()
        plugin.reloadConfig()
        FolraTranslate(plugin)

        onReload(sender, args)
    }

    protected open fun onReload(sender: CommandSender, args: Array<out String>) {}
}
