package com.github.grassproject.folra.util.pdc

import com.github.grassproject.folra.util.item.ItemEncoder
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType

object ItemStackDataType : PersistentDataType<String, ItemStack> {
    override fun getPrimitiveType(): Class<String> = String::class.java
    override fun getComplexType(): Class<ItemStack> = ItemStack::class.java

    override fun toPrimitive(complex: ItemStack, context: PersistentDataAdapterContext): String {
        return ItemEncoder.encode(complex)
    }

    override fun fromPrimitive(primitive: String, context: PersistentDataAdapterContext): ItemStack {
        return ItemEncoder.decode(primitive)
    }
}