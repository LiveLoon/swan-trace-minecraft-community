package com.example.swantracemc.data

data class TopResponse(
    val stat: String,
    val display: String,
    val format: String,
    val entries: List<TopEntry>
)
data class TopEntry(val rank: Int, val uuid: String, val name: String?, val value: Long, val value_human: String)