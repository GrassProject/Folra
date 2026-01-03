package com.github.grassproject.folra.provider

import com.github.grassproject.folra.api.integration.CustomItemProvider
import dev.lone.itemsadder.api.CustomBlock
import dev.lone.itemsadder.api.CustomFurniture
import dev.lone.itemsadder.api.CustomStack
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.inventory.ItemStack

object ItemsAdderProvider : CustomItemProvider {
    override fun isCustomBlock(block: Block): Boolean {
        return CustomBlock.byAlreadyPlaced(block) != null
    }

    override fun blockID(block: Block): String? {
        return CustomBlock.byAlreadyPlaced(block)?.namespacedID
    }

    override fun isFurniture(entity: Entity): Boolean {
        return CustomFurniture.byAlreadySpawned(entity) != null
    }

    override fun furnitureID(entity: Entity): String? {
        return CustomFurniture.byAlreadySpawned(entity)?.namespacedID
    }

    override fun itemID(itemStack: ItemStack): String? {
        return CustomStack.byItemStack(itemStack)?.namespacedID
    }

    override fun itemStack(id: String): ItemStack? {
        return CustomStack.getInstance(id)?.itemStack
    }

    override val identifier: String
        get() = "ItemsAdder"
}