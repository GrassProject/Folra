package com.github.grassproject.folra.util

import com.github.grassproject.folra.api.FolraPlugin
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit

object FolraLogger(private val plugin: FolraPlugin? = null) {
    private val prefix = "<#96f19c>{gp-prefix}</#96f19c><#989c99> > </#989c99>"

    private fun format(color: String, message: String): Component {
        val name = plugin?.name ?: "Folra"
        return "${prefix.replace("{gp-prefix}", name)}<$color>$message</$color>".toMiniMessage()
    }

    fun info(string: String) = send("white", string)
    fun warning(string: String) = send("yellow", string)
    fun suc(string: String) = send("green", string)
    fun bug(string: String) = send("red", string)

    private fun send(color: String, string: String) {
        Bukkit.getConsoleSender().sendMessage(format(color, string))
    }

    companion object {
        @JvmStatic
        @JvmName("log")
        fun info(string: String) = FolraLogger().info(string)

        @JvmStatic
        @JvmName("logSuc")
        fun suc(string: String) = FolraLogger().suc(string)

        @JvmStatic
        @JvmName("warn")
        fun warning(string: String) = FolraLogger().warning(string)

        @JvmStatic
        @JvmName("severe")
        fun bug(string: String) = FolraLogger().bug(string)
    }
}