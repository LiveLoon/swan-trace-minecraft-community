package com.example.swantracemc.network

import com.example.swantracemc.data.*
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("player/list")
    suspend fun getPlayerList(): List<Player>

    @GET("stats")
    suspend fun getStats(): StatsResponse

    @GET("top/{statKey}")
    suspend fun getTop(@Path("statKey") statKey: String): TopResponse

    @GET("backup/list")
    suspend fun getBackupList(): List<BackupFile>


    // ============================================================
    // 服务器信息
    // ============================================================

    @GET("server/info")
    suspend fun getServerInfo(): ServerInfo


    // ============================================================
    // 服务器实时监控
    // ============================================================

    @GET("server/monitor")
    suspend fun getServerMonitor(): ServerMonitor
}