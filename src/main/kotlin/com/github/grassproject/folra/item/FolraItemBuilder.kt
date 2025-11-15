package com.github.grassproject.folra.item

import com.github.grassproject.folra.util.toMiniMessage
import io.papermc.paper.datacomponent.DataComponentTypes
import io.papermc.paper.datacomponent.item.CustomModelData
import io.papermc.paper.datacomponent.item.DyedItemColor
import io.papermc.paper.datacomponent.item.ItemEnchantments
import io.papermc.paper.registry.RegistryAccess
import io.papermc.paper.registry.RegistryKey
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemRarity
import org.bukkit.inventory.ItemStack
import java.util.Locale

class FolraItemBuilder(
    private val baseItem: ItemStack
) {

    constructor(material: Material) : this(ItemStack(material))

    private val resultItem = baseItem.clone()

    private fun String.mm() =
        this.toMiniMessage().decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)

    fun setAmount(amount: Int) = apply {
        resultItem.amount = amount
    }

    fun setDisplayName(displayName: String) = apply {
        resultItem.editMeta { meta ->
            meta.displayName(displayName.mm())
        }
    }

    fun setItemName(itemName: String) = apply {
        resultItem.setData(DataComponentTypes.ITEM_NAME, itemName.mm())
    }

    fun setCustomModelData(
        colors: List<Color>,
        floats: List<Float>,
        flags: List<Boolean>,
        strings: List<String>
    ) = apply {
        resultItem.setData(
            DataComponentTypes.CUSTOM_MODEL_DATA,
            CustomModelData.customModelData().apply {
                if (colors.isNotEmpty()) addColors(colors)
                if (floats.isNotEmpty()) addFloats(floats)
                if (flags.isNotEmpty()) addFlags(flags)
                if (strings.isNotEmpty()) addStrings(strings)
            }
        )
    }

    fun setDamage(damage: Int) = apply {
        resultItem.setData(DataComponentTypes.DAMAGE, damage)
    }

    fun setDye(color: Color) = apply {
        resultItem.setData(DataComponentTypes.DYED_COLOR, DyedItemColor.dyedItemColor(color))
    }

    fun setEnchants(enchants: Map<String, Int>) = apply {
        if (enchants.isEmpty()) return@apply

        val mapped = enchants
            .mapNotNull { (ench, level) ->
                getEnchantmentByString(ench)?.let { it to level }
            }
            .toMap()

        if (mapped.isEmpty()) return@apply

        val dataType =
            if (resultItem.type == Material.ENCHANTED_BOOK) DataComponentTypes.STORED_ENCHANTMENTS
            else DataComponentTypes.ENCHANTMENTS

        resultItem.setData(
            dataType,
            ItemEnchantments.itemEnchantments().addAll(mapped).build()
        )
    }

    fun addItemFlags(itemFlags: Collection<ItemFlag>) = apply {
        if (itemFlags.isNotEmpty())
            resultItem.addItemFlags(*itemFlags.toTypedArray())
    }

    fun setItemModel(itemModel: Key) = apply {
        resultItem.setData(DataComponentTypes.ITEM_MODEL, itemModel)
    }

    fun setLore(lines: List<String>) = apply {
        resultItem.editMeta { meta ->
            meta.lore(lines.map { it.mm() })
        }
    }

    fun setMaxDamage(maxDamage: Int) = apply {
        resultItem.setData(DataComponentTypes.MAX_DAMAGE, maxDamage)
    }

    fun setMaxStackSize(maxStackSize: Int) = apply {
        resultItem.setData(DataComponentTypes.MAX_STACK_SIZE, maxStackSize)
    }

    fun setRarity(rarity: ItemRarity) = apply {
        resultItem.setData(DataComponentTypes.RARITY, rarity)
    }

    fun setTooltipStyle(tooltipStyle: Key) = apply {
        resultItem.setData(DataComponentTypes.TOOLTIP_STYLE, tooltipStyle)
    }

    fun setUnbreakable() = apply {
        resultItem.setData(DataComponentTypes.UNBREAKABLE)
    }

    fun getBaseItem(): ItemStack = baseItem

    fun build(): ItemStack = resultItem.clone()

    private fun getEnchantmentByString(name: String): Enchantment? {
        return RegistryAccess.registryAccess()
            .getRegistry(RegistryKey.ENCHANTMENT)
            .get(NamespacedKey.minecraft(name.lowercase(Locale.getDefault())))
    }
}
