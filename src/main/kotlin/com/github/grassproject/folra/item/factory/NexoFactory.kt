package com.github.grassproject.folra.item.factory

import com.github.grassproject.folra.item.ItemHandler
import com.nexomc.nexo.api.NexoItems
import org.bukkit.inventory.ItemStack

object NexoFactory: ItemHandler.Factory {
    override fun create(id: String): ItemStack? {
        return NexoItems.itemFromId(id)?.build()
    }
}