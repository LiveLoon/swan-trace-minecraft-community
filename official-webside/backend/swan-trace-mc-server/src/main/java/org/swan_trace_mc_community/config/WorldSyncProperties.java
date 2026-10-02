package org.swan_trace_mc_community.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;

@Data
@Configuration
@ConfigurationProperties(prefix = "world-sync")
public class WorldSyncProperties {

    private String directory = "/root/world";

    private String token =
            "SwanTrace-WorldSync-2026";

    private boolean timeRestrictionEnabled = true;

    private LocalTime allowedStart =
            LocalTime.of(9, 0);

    private LocalTime allowedEnd =
            LocalTime.of(10, 0);

    /**
     * SHA-256 缓冲区大小。
     */
    private int hashBufferSize =
            1024 * 1024;

    /**
     * 文件稳定检测次数。
     */
    private int stableCheckRetries = 3;

    /**
     * Range 下载大小。
     */
    private long rangeSize =
            8L * 1024L * 1024L;

    /**
     * 有玩家在线时禁止同步。
     */
    private boolean denyWhenPlayersOnline = true;

    /**
     * Minecraft 玩家 API。
     */
    private String playerApiUrl =
            "http://swan-trace-mc:3000/open-api/players";

    /**
     * WatchService 文件修改 debounce。
     *
     * Minecraft 写 region 文件时可能连续触发
     * 多次 MODIFY。
     */
    private long watchDebounceMillis = 2000;
}