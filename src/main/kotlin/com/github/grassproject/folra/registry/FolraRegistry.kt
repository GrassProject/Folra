package com.github.grassproject.folra.registry

import com.github.grassproject.folra.provider.Base64Provider
import com.github.grassproject.folra.provider.CraftEngineProvider
import com.github.grassproject.folra.provider.ItemsAdderProvider
import com.github.grassproject.folra.provider.NexoProvider


object FolraRegistry {

    val ITEM_PROVIDERS = hashMapOf(
        "ITEMSADDER" to ItemsAdderProvider,
        "CRAFTENGINE" to CraftEngineProvider,
        "BASE64" to Base64Provider,
        "NEXO" to NexoProvider
    )

}