package com.example.swantracemc.data

data class Player(
    val name: String?,
    val uuid: String,
    val gamemode: String,
    val ping: Int?,
    val isOnline: Boolean,
    val isBanned: Boolean
)