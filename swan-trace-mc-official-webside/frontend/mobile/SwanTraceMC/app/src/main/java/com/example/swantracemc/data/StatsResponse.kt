package com.example.swantracemc.data

data class StatsResponse(val stats: List<StatType>)
data class StatType(val key: String, val display: String, val category: String, val format: String)