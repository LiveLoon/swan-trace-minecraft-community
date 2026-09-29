package com.example.swantracemc.data

data class ServerInfo(
    val motd: String?,
    val maxPlayerCount: Int,
    val system: ServerSystemInfo,
    val code: Int,
    val port: Int,
    val ingameTime: Long,
    val whitelist: Boolean,
    val error: String?,
    val uptime: Long
)

data class ServerSystemInfo(
    val memory: Long,
    val java: String?,
    val os: String?,
    val gpus: List<String>?,
    val arch: String?,
    val cpuThread: Int,
    val cpuName: String?,
    val cpuCore: Int
)