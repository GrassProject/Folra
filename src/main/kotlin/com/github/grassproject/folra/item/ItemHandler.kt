package com.github.grassproject.folra.item

import com.github.grassproject.folra.registry.FolraRegistry
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object ItemHandler {

    fun itemStackById(namespace: String): ItemStack? {
        return if (namespace.contains(":")) {
            val id = namespace.split(":").first().uppercase()
            val provider = FolraRegistry.ITEM_PROVIDERS[id] ?: return null
            provider.itemStack(namespace.substring(id.length + 1))
        } else {
            val material = Material.getMaterial(namespace.uppercase()) ?: return null
            ItemStack(material)
        }
    }
}