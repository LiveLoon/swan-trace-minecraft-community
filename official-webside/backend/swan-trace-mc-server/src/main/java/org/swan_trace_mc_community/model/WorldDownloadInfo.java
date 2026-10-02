package org.swan_trace_mc_community.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorldDownloadInfo {

    /**
     * 相对路径
     */
    private String path;

    /**
     * 文件大小
     */
    private long size;

    /**
     * SHA-256
     */
    private String sha256;

    /**
     * 最后修改时间
     */
    private long lastModified;
}