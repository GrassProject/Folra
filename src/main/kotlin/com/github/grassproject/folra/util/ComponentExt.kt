package com.github.grassproject.folra.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

fun Component.broadcast() {
    Bukkit.broadcast(this)
}

fun Component.toMiniMessageString(): String =
    MiniMessage.miniMessage().serialize(this)

fun Component.toPlain(): String {
    return PlainTextComponentSerializer.plainText().serialize(this)
}

fun Component.toJson(): String {
    return GsonComponentSerializer.gson().serialize(this)
}

fun Component.send(sender: CommandSender) {
    sender.sendMessage(this)
}

fun Component.send(player: Player) {
    player.sendMessage(this)
}

fun CommandSender.send(component: Component) {
    component.send(this)
}