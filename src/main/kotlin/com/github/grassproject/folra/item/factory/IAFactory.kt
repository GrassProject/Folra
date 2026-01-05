package com.github.grassproject.folra.item.factory

import com.github.grassproject.folra.item.ItemHandler
import dev.lone.itemsadder.api.CustomStack
import org.bukkit.inventory.ItemStack

object IAFactory : ItemHandler.Factory {
    override fun create(id: String): ItemStack? {
        return CustomStack.getInstance(id)?.itemStack
    }
}