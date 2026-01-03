package com.github.grassproject.folra.item

import net.kyori.adventure.text.Component

interface Item {
    val amount: Int
    val displayName: Component?
    val lore: List<Component>?
}