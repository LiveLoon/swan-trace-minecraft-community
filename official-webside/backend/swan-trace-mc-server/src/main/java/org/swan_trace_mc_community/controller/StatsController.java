package org.swan_trace_mc_community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class StatsController {
    @Value("${stats.api.base-url}")
    private String statsApiBaseUrl;
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("stats")
    public ResponseEntity<?> getStats(){
        String url = statsApiBaseUrl + "/stats";
        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }


    @GetMapping("top/{stat}")
    public ResponseEntity<?> getTopStat(@PathVariable String stat) {
        String url = statsApiBaseUrl + "/top/" + stat;
        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
