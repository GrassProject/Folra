package com.github.grassproject.folra.item

import org.bukkit.inventory.ItemStack

object ItemHandler {

    interface Factory {
        fun create(id: String): ItemStack?
    }
}