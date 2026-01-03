package com.github.grassproject.folra.hangul

class Hangul {
    fun isHangul(input: String): Boolean {
        if (input.length >= 20) {
            val length= input.length
            for (i in 0 until length step 10) {
                val end= if (i + 10 > length) length else i + 10
                val substring= input.substring(i, end)
                for (char in substring) {
                    val code = char.code
                    return range(code)
                }
            }
            return true
        } else {
            for (char in input) {
                val code = char.code
                return range(code)
            }
            return true
        }
    }

    fun incode(input: String): String {
        val builder= StringBuilder()
        for (char in input) {
            if (char=='.') {
                builder.append("..")
                continue
            }
            val code = char.code
            builder.append("${code}.")
        }
        builder.delete(builder.length - 1, builder.length)
        return builder.toString()
    }

    fun decode(input: String): String {
        val parts= input.split(".")
        val builder= StringBuilder()
        for (part in parts) {
            if (part.isEmpty()) continue
            val code= part.toInt()
            val char= code.toChar()
            builder.append(char)
        }
        return builder.toString()
    }

    private fun range(c: Int): Boolean {
        return (c in 0xAC00..0xD7A3) || (c in 0x1100..0x11FF) || (c in 0x3131..0x318F)
    }
}

/*
* U+AC00 ~ U+D7A3 한글 음절
* U+1100 ~ U+11FF 한글 자모
* U+3131 ~ U+318F 한글 호환 자모
* */