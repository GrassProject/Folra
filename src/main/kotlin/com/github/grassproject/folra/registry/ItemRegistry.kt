package com.github.grassproject.folra.registry

import com.github.grassproject.folra.item.FolraItem
import com.github.grassproject.folra.item.FolraItemInteractEvent
import com.github.grassproject.folra.item.ItemHandler
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import kotlin.collections.get

fun FolraItem.register(
    namespace: String,
    id: String
): Boolean {
    return register(
        namespace, id,
        registerInteraction = false
    )
}

fun FolraItem.register(
    namespace: String,
    id: String,
    interactionHandler: (FolraItemInteractEvent) -> Unit
): Boolean {
    return register(namespace, id, interactionHandler, true)
}

private fun FolraItem.register(
    namespace: String, id: String,
    interactionHandler: (FolraItemInteractEvent) -> Unit = {}, registerInteraction: Boolean
): Boolean {
    val registryId = registryId()
    val item = getUnmodifiedItem()
    if (registryId != null && registryId != "$namespace:$id") return false
    val meta = item.itemMeta ?: return false
    meta.persistentDataContainer.set(ItemHandler.NAMESPACE_KEY, PersistentDataType.STRING, "$namespace:$id")
    item.itemMeta = meta
    FolraRegistry.ITEM["$namespace:$id"] = this

    if (registerInteraction) {
        ItemHandler.listenInteractions["$namespace:$id"] = interactionHandler
    }
    return true
}

fun FolraItem.setInteractionHandler(interactionHandler: (FolraItemInteractEvent) -> Unit): Boolean {
    val registryId = registryId() ?: return false
    ItemHandler.listenInteractions[registryId] = interactionHandler
    return true
}

fun FolraItem.removeInteractionHandler(): Boolean {
    val registryId = registryId() ?: return false
    ItemHandler.listenInteractions.remove(registryId)
    return true
}

fun FolraItem.unregister(): Boolean {
    val registryId = registryId() ?: return false
    val item = getUnmodifiedItem()
    val meta = item.itemMeta ?: return false
    meta.persistentDataContainer.remove(ItemHandler.NAMESPACE_KEY)
    item.itemMeta = meta
    FolraRegistry.ITEM.remove(registryId)
    ItemHandler.listenInteractions.remove(registryId)
    return true
}

fun FolraItem.registryId(): String? {
    val meta = getUnmodifiedItem().itemMeta
    val pdc = meta?.persistentDataContainer ?: return null
    return pdc.get(ItemHandler.NAMESPACE_KEY, PersistentDataType.STRING)
}

fun FolraRegistry.getItem(id: String): FolraItem? {
    return ITEM[id]
}

fun FolraRegistry.getItem(itemStack: ItemStack): FolraItem? {
    val pdc = itemStack.itemMeta?.persistentDataContainer ?: return null
    val namespacedKey = ItemHandler.NAMESPACE_KEY
    if (!pdc.has(namespacedKey, PersistentDataType.STRING)) return null
    val id = pdc.get(namespacedKey, PersistentDataType.STRING)
    return ITEM[id]
}

fun ItemStack.isFolraItem(): FolraItem? {
    val meta = itemMeta ?: return null
    val pdc = meta.persistentDataContainer
    val namespacedKey = ItemHandler.NAMESPACE_KEY
    if (!pdc.has(namespacedKey, PersistentDataType.STRING)) return null
    val id = pdc.get(namespacedKey, PersistentDataType.STRING)
    return FolraRegistry.ITEM[id]
}
