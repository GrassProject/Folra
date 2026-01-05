package com.github.grassproject.folra.api.event

import org.bukkit.event.Cancellable

abstract class CancellableFolraEvent(async: Boolean = false) : FolraEvent(async), Cancellable {

    private var cancelled = false

    override fun isCancelled(): Boolean {
        return cancelled
    }

    override fun setCancelled(cancel: Boolean) {
        cancelled = cancel
    }

}