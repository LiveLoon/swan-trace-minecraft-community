package com.example.swantracemc.data

data class ServerMonitor(
    val memory: Double,
    val code: Int,
    val tps: Double,
    val cpu: Double,
    val error: String?
)