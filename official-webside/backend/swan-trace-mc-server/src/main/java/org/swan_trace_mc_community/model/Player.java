package org.swan_trace_mc_community.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {
    private String name;
    @JsonProperty("isOnline")
    private boolean isOnline;

    @JsonProperty("isBanned")
    private boolean isBanned;
    private String uuid;
    private String gamemode;
    private Integer ping;
}