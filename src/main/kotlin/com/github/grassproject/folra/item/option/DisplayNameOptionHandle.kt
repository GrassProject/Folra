package com.github.grassproject.folra.item.option

import com.github.grassproject.folra.util.toMiniMessage
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.meta.ItemMeta

class DisplayNameOptionHandle(
    val displayName: String
): ItemOptionHandle {
    override val key = Companion.key

    override fun apply(itemMeta: ItemMeta) {
        itemMeta.displayName(displayName.toMiniMessage().decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE))
    }

    companion object: ItemOption {
        override val key = Key.key("itemoption:display-name")
        override fun load(section: ConfigurationSection): ItemOptionHandle? {
            val displayName = section.getString("display-name") ?: return null
            return DisplayNameOptionHandle(displayName)
        }
    }

}