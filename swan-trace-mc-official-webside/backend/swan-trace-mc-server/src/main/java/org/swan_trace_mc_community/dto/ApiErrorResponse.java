
        package org.swan_trace_mc_community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {

    /**
     * HTTP / 业务状态码
     */
    private int code;

    /**
     * 错误消息
     */
    private String message;

    /**
     * 额外数据
     */
    private Object data;
}

