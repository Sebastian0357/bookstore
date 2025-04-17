package com.bookstore.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * @ClassName FileController
 * @Author Kangyx w13694105330@163.com
 * @Date 2025/3/24 23:51
 * @Version V1.0
 **/
@RestController
@RequestMapping("/upload")
public class FileController {

    private static final String UPLOAD_DIR = "uploads/"; // 上传文件存放目录

    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("文件不能为空");
        }

        try {
            // 获取文件名
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            // 构建保存路径
            Path filePath = Paths.get(UPLOAD_DIR + fileName);

            // 确保目录存在
            Files.createDirectories(filePath.getParent());

            // 保存文件到服务器
            Files.write(filePath, file.getBytes());

            // 返回文件访问 URL
            String fileUrl = "http://localhost:1118/uploads/" + fileName;
            return ResponseEntity.ok(Map.of("message", "上传成功", "url", fileUrl));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("文件上传失败: " + e.getMessage());
        }
    }
}

