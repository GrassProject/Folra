package com.github.grassproject.folra.provider
import com.github.grassproject.folra.api.integration.CustomItemProvider
import com.github.grassproject.folra.util.item.ItemEncoder
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.inventory.ItemStack

object Base64Provider : CustomItemProvider {
    override fun isCustomBlock(block: Block): Boolean = false
    override fun blockID(block: Block): String? = null
    override fun isFurniture(entity: Entity): Boolean = false
    override fun furnitureID(entity: Entity): String? = null

    override fun itemID(itemStack: ItemStack): String? {
        return null
    }

    override fun itemStack(id: String): ItemStack? {
        return ItemEncoder.decode(id)
    }

    override val identifier: String
        get() = "Base64"
}