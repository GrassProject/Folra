package com.github.grassproject.folra.item.factory

import com.github.grassproject.folra.item.ItemHandler
import com.github.grassproject.folra.util.item.ItemEncoder
import org.bukkit.inventory.ItemStack

object Base64Factory : ItemHandler.Factory {
    override fun create(id: String): ItemStack? {
        return ItemEncoder.decode(id)
    }
}