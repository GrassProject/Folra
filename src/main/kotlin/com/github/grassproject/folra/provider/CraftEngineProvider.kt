package com.github.grassproject.folra.provider

import com.github.grassproject.folra.api.integration.CustomItemProvider
import net.momirealms.craftengine.bukkit.api.CraftEngineBlocks
import net.momirealms.craftengine.bukkit.api.CraftEngineFurniture
import net.momirealms.craftengine.bukkit.api.CraftEngineItems
import net.momirealms.craftengine.core.item.ItemBuildContext
import net.momirealms.craftengine.core.plugin.context.ContextHolder
import net.momirealms.craftengine.core.util.Key
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.inventory.ItemStack

object CraftEngineProvider : CustomItemProvider {
    override fun isCustomBlock(block: Block): Boolean {
        return CraftEngineBlocks.isCustomBlock(block)
    }

    override fun blockID(block: Block): String {
        return CraftEngineBlocks.getCustomBlockState(block)?.owner()?.value()
            ?.id().toString()
    }

    override fun isFurniture(entity: Entity): Boolean {
        return CraftEngineFurniture.isFurniture(entity)
    }

    override fun furnitureID(entity: Entity): String {
        return CraftEngineFurniture.getLoadedFurnitureByBaseEntity(entity)
            ?.id().toString()
    }

    override fun itemID(itemStack: ItemStack): String {
        return CraftEngineItems.getCustomItemId(itemStack).toString()
    }

    override fun itemStack(id: String): ItemStack? {
        return CraftEngineItems.byId(Key.of(id))
            ?.buildItemStack(ItemBuildContext.of(null, ContextHolder.empty()))
    }

    override val identifier: String
        get() = "CraftEngine"
}