package com.github.grassproject.folra.provider

import com.github.grassproject.folra.api.integration.CustomItemProvider
import com.nexomc.nexo.api.NexoBlocks
import com.nexomc.nexo.api.NexoFurniture
import com.nexomc.nexo.api.NexoItems
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.inventory.ItemStack

object NexoProvider : CustomItemProvider {
    init {

    }

    override fun isCustomBlock(block: Block): Boolean {
        return NexoBlocks.isCustomBlock(block)
    }

    override fun blockID(block: Block): String? {
        return NexoBlocks.customBlockMechanic(block)?.itemID
    }

    override fun isFurniture(entity: Entity): Boolean {
        return NexoFurniture.isFurniture(entity)
    }

    override fun furnitureID(entity: Entity): String? {
        return NexoFurniture.furnitureMechanic(entity)?.itemID
    }

    override fun itemID(itemStack: ItemStack): String? {
        return NexoItems.idFromItem(itemStack)
    }

    override fun itemStack(id: String): ItemStack? {
        return NexoItems.itemFromId(id)?.build()
    }

    override val identifier: String
        get() = "Nexo"
}