package org.swan_trace_mc_community.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileItem {
    public String name;
    public String type;
    public String mtime;
    public long size;
}
