package com.github.grassproject.folra.item.factory

import com.github.grassproject.folra.item.ItemHandler
import net.Indyuce.mmoitems.MMOItems
import org.bukkit.inventory.ItemStack

object MMOItemsFactory : ItemHandler.Factory {
    override fun create(id: String): ItemStack? {
        val args = id.split(":")
        return MMOItems.plugin.getItem(args[0], args[1])
    }
}