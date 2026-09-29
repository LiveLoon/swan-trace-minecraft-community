package org.swan_trace_mc_community.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("server")
public class ServerController {
    private final RestTemplate restTemplate;
    @Value("${server-api.base-url}")
    private String serverApiBaseUrl;
    public ServerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("info")
    public ResponseEntity<?> getInfo(){
        String url = serverApiBaseUrl + "/info";

        ResponseEntity<Object> response =
                restTemplate.getForEntity(url, Object.class);

        return ResponseEntity
                .status(response.getStatusCode())
                .body(response.getBody());
    }

    /**
     * 获取服务器实时监控信息
     */
    @GetMapping("monitor")
    public ResponseEntity<?> getMonitor() {
        String url = serverApiBaseUrl + "/monitor";

        ResponseEntity<Object> response =
                restTemplate.getForEntity(url, Object.class);

        return ResponseEntity
                .status(response.getStatusCode())
                .body(response.getBody());
    }

    /**
     * 获取服务器插件列表
     */
    @GetMapping("plugins")
    public ResponseEntity<?> getPlugins() {
        String url = serverApiBaseUrl + "/plugins";

        ResponseEntity<Object> response =
                restTemplate.getForEntity(url, Object.class);

        return ResponseEntity
                .status(response.getStatusCode())
                .body(response.getBody());
    }
}
