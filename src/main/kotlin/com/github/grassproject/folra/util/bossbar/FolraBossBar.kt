package com.github.grassproject.folra.util.bossbar

import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import java.util.EnumSet

class FolraBossBar(
    title: Component,
    color: BossBar.Color,
    overlay: BossBar.Overlay,
    flags: MutableSet<BossBar.Flag>,
    progress: Float
) {

    private val bossBar: BossBar = BossBar.bossBar(title, progress, color, overlay, EnumSet.copyOf(flags))

    var title: Component = title
        set(value) {
            field = value
            bossBar.name(value)
        }

    var color: BossBar.Color = color
        set(value) {
            field = value
            bossBar.color(value)
        }

    var overlay: BossBar.Overlay = overlay
        set(value) {
            field = value
            bossBar.overlay(value)
        }

    private var _flags: EnumSet<BossBar.Flag> = EnumSet.copyOf(flags)
    val flags: Set<BossBar.Flag> get() = _flags.toSet()

    fun addFlag(flag: BossBar.Flag) {
        if (_flags.add(flag)) bossBar.flags(_flags)
    }

    fun removeFlag(flag: BossBar.Flag) {
        if (_flags.remove(flag)) bossBar.flags(_flags)
    }

    fun setFlags(flags: Set<BossBar.Flag>) {
        _flags.clear()
        _flags.addAll(flags)
        bossBar.flags(_flags)
    }

    var progress: Float = progress
        set(value) {
            field = value
            bossBar.progress(value)
        }

    fun addViewer(player: Player) = bossBar.addViewer(player)
    fun removeViewer(player: Player) = bossBar.removeViewer(player)
}