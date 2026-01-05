package com.github.grassproject.folra.item

import com.github.grassproject.folra.Folra
import com.github.grassproject.folra.item.option.ItemOptionHandle
import com.github.grassproject.folra.registry.isFolraItem
import com.github.grassproject.folra.registry.registryId
import com.github.grassproject.folra.util.call
import com.github.grassproject.folra.util.event
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventPriority
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

object ItemHandler {

    val NAMESPACE_KEY by lazy {
        NamespacedKey(Folra.INSTANCE, "Custom_Item_Registry")
    }
    val listenInteractions = mutableMapOf<String, (FolraItemInteractEvent) -> Unit>()

    fun initialize(folra: Folra) {
        event<PlayerInteractEvent>(ignoreCancelled = true, EventPriority.LOW) {
            if (it.hand == EquipmentSlot.OFF_HAND) return@event
            if (listenInteractions.isEmpty()) return@event
            val item = it.item ?: return@event
            val aitem = item.isFolraItem() ?: return@event
            val registry = aitem.registryId() ?: return@event

            val interaction = listenInteractions[registry] ?: return@event

            val interactType = when (it.action) {
                Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK -> if (it.player.isSneaking) FolraItemInteractEvent.InteractType.SHIFT_RIGHT else FolraItemInteractEvent.InteractType.RIGHT
                Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK -> if (it.player.isSneaking) FolraItemInteractEvent.InteractType.SHIFT_LEFT else FolraItemInteractEvent.InteractType.LEFT
                else -> return@event
            }

            val aitemEvent = FolraItemInteractEvent(
                it.player, aitem, item, it, interactType,
            )
            interaction(aitemEvent)
            aitemEvent.call()
        }
        event<PlayerSwapHandItemsEvent> {
            val item = it.mainHandItem
            val aitem = item.isFolraItem() ?: return@event
            val registry = aitem.registryId() ?: return@event
            val interaction = listenInteractions[registry] ?: return@event

            val interactType =
                if (it.player.isSneaking) FolraItemInteractEvent.InteractType.SHIFT_SWAP else FolraItemInteractEvent.InteractType.SWAP
            val aitemEvent = FolraItemInteractEvent(
                it.player, aitem, item, it, interactType,
            )
            interaction(aitemEvent)
            aitemEvent.call()
        }
        event<InventoryClickEvent> {
            val player = it.whoClicked as? Player ?: return@event
            val item = it.currentItem ?: return@event
            val aitem = item.isFolraItem() ?: return@event
            val registry = aitem.registryId() ?: return@event
            val interaction = listenInteractions[registry] ?: return@event

            val interactType = when (it.click) {
                ClickType.SHIFT_LEFT -> FolraItemInteractEvent.InteractType.INVENTORY_SHIFT_LEFT
                ClickType.LEFT -> FolraItemInteractEvent.InteractType.INVENTORY_LEFT
                ClickType.SHIFT_RIGHT -> FolraItemInteractEvent.InteractType.INVENTORY_SHIFT_RIGHT
                ClickType.RIGHT -> FolraItemInteractEvent.InteractType.INVENTORY_RIGHT
                ClickType.NUMBER_KEY -> {
                    when (it.hotbarButton) {
                        1 -> FolraItemInteractEvent.InteractType.NUM_1
                        2 -> FolraItemInteractEvent.InteractType.NUM_2
                        3 -> FolraItemInteractEvent.InteractType.NUM_3
                        4 -> FolraItemInteractEvent.InteractType.NUM_4
                        5 -> FolraItemInteractEvent.InteractType.NUM_5
                        6 -> FolraItemInteractEvent.InteractType.NUM_6
                        7 -> FolraItemInteractEvent.InteractType.NUM_7
                        8 -> FolraItemInteractEvent.InteractType.NUM_8
                        9 -> FolraItemInteractEvent.InteractType.NUM_9
                        else -> FolraItemInteractEvent.InteractType.NUM_0
                    }
                }

                ClickType.DROP -> FolraItemInteractEvent.InteractType.INVENTORY_DROP
                ClickType.SWAP_OFFHAND -> FolraItemInteractEvent.InteractType.INVENTORY_SWAP
                else -> return@event
            }
            val aitemEvent = FolraItemInteractEvent(
                player,
                aitem,
                item,
                it,
                interactType,
            )
            interaction(aitemEvent)
            aitemEvent.call()
        }
    }

    fun disable(folra: Folra) {

    }

    fun create(
        internalId: String?,
        item: ItemStack,
        options: List<ItemOptionHandle>
    ): FolraItem {
        return FolraItem(
            internalId,
            item,
            options
        )
    }

    interface Factory {

        fun create(id: String): ItemStack?

    }

}