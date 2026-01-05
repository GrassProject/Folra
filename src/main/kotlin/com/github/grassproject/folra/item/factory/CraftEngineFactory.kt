package com.github.grassproject.folra.item.factory

import com.github.grassproject.folra.item.ItemHandler
import net.momirealms.craftengine.bukkit.api.CraftEngineItems
import net.momirealms.craftengine.core.item.ItemBuildContext
import net.momirealms.craftengine.core.plugin.context.ContextHolder
import net.momirealms.craftengine.core.util.Key
import org.bukkit.inventory.ItemStack

object CraftEngineFactory : ItemHandler.Factory {
    override fun create(id: String): ItemStack? {
        return CraftEngineItems.byId(Key.of(id))
            ?.buildItemStack(ItemBuildContext.of(null, ContextHolder.empty()))
    }
}