package org.swan_trace_mc_community.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.swan_trace_mc_community.model.FileItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("backup")
public class BackupController {
    @Value("${backup.dir}")
    private String backupDir;



    @GetMapping("list")
    public ResponseEntity<?> list(){
        File dir = new File(backupDir);
        if (!dir.exists() || !dir.isDirectory()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        File[] files = dir.listFiles();
        if (files != null) {
            // 将 File 对象映射为 FileItem 对象
            List<FileItem> fileList = Arrays.stream(files)
                    .filter(File::isFile) // 只列出文件
                    .map(file -> {
                        String mtime = String.valueOf(file.lastModified()); // 获取最后修改时间戳
                        String type = "file"; // 可以根据需要扩展，例如根据后缀名判断
                        return new FileItem(file.getName(), type, mtime, file.length());
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(fileList);
        } else {
            // 目录无法读取
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("download/{filename}")
    public void download(@PathVariable String filename, HttpServletResponse response) throws IOException {
        Path filePath = Paths.get(backupDir, filename);
        File file = filePath.toFile();
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        response.setHeader("Accept-Ranges", "bytes");
        response.setContentLengthLong(file.length());

        try (InputStream is = new FileInputStream(file);
             OutputStream os = response.getOutputStream()) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
        }
    }
}