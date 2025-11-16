package com.github.grassproject.folra.command.impl

import com.github.grassproject.folra.command.FolraBaseCommand
import com.github.grassproject.folra.util.item.ItemEncoder
import com.github.grassproject.folra.util.toMiniMessage
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object ItemConvertCommand : FolraBaseCommand(
    "itemconvert", "ItemConvert command", listOf(), mutableListOf()
) {
    override fun executeCommand(sender: CommandSender, args: Array<out String>) {
        if (sender !is Player) return

        val item = sender.inventory.itemInMainHand
        if (item.type.isAir) {
            sender.sendMessage("손에 아이템을 들고 다시 시도하십시오.")
            return
        }

        val base64 = ItemEncoder.encode(item)
        sender.sendMessage("성공적으로 아이템을 변환하였습니다! (<u><click:COPY_TO_CLIPBOARD:'$base64'>Click to copy</click></u>)".toMiniMessage())
        return
    }

}