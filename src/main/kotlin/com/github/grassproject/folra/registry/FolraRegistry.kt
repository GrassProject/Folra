package com.github.grassproject.folra.registry

import com.github.grassproject.folra.item.FolraItem
import com.github.grassproject.folra.item.factory.*

object FolraRegistry {

    val ITEM_FACTORIES = hashMapOf(
        "ITEMSADDER" to IAFactory,
        "CRAFTENGINE" to CraftEngineFactory,
        "BASE64" to Base64Factory,
        "NEXO" to NexoFactory
    )

    val ITEM = HashMap<String, FolraItem>()

}