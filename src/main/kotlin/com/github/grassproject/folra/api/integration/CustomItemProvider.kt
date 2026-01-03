package com.github.grassproject.folra.api.integration

import com.github.frixel.frixelengine.api.integration.ExternalProvider
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.inventory.ItemStack

interface CustomItemProvider : ExternalProvider {

    fun isCustomBlock(block: Block): Boolean
    fun blockID(block: Block): String?
    // fun placeCustomBlock(location: Location, id: String): Boolean
    // fun removeCustomBlock(location: Location): Boolean

    fun isFurniture(entity: Entity): Boolean
    fun furnitureID(entity: Entity): String?
    // fun placeFurniture(location: Location, id: String): Entity?
    // fun removeFurniture(entity: Entity): Boolean

    fun itemID(itemStack: ItemStack): String?
    fun itemStack(id: String): ItemStack?

}