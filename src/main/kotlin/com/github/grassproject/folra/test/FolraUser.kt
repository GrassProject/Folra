package com.github.grassproject.folra.test

import java.util.*

// CMI USER
data class FolraUser(
    val id: Int = 0,
    val uuid: UUID,
    var username: String,
    var nickname: String? = null
)