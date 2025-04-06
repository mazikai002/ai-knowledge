package com.itheima.backend.controller;

import com.itheima.backend.common.Result;
import com.itheima.backend.model.vo.KnowledgeVO;
import com.itheima.backend.service.KnowledgeUploadService;
import com.itheima.backend.service.ex.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 知识库文档上传控制器
 * 
 * @author developer
 * @date 2025/04/06
 */
@Slf4j
@RestController
@RequestMapping("/api/knowledge/file")
@RequiredArgsConstructor
public class KnowledgeUploadController {
    
    private final KnowledgeUploadService knowledgeUploadService;
    
    /**
     * 上传知识库文档
     * 
     * @param file 文档文件
     * @param title 标题
     * @param tags 标签
     * @param description 描述
     * @param createBy 创建者ID
     * @return 上传结果
     */
    @PostMapping("/upload")
    public Result<KnowledgeVO> uploadKnowledge(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "tags", required = false) String tags,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "createBy", required = false) Long createBy) {
        
        log.info("接收到上传请求: 文件名={}, 大小={}字节, 内容类型={}, 标题={}, 创建者ID={}",
                file.getOriginalFilename(),
                file.getSize(),
                file.getContentType(),
                title,
                createBy);
        
        try {
            KnowledgeVO knowledgeVO = knowledgeUploadService.uploadKnowledge(file, title, tags, description, createBy);
            log.info("文档上传成功: ID={}, 标题={}", knowledgeVO.getId(), knowledgeVO.getTitle());
            return Result.success(knowledgeVO);
        } catch (BusinessException e) {
            log.error("业务异常: code={}, message={}", e.getCode(), e.getMessage());
            return Result.error(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("上传文档未预期异常: {}", e.getMessage(), e);
            return Result.error(500, "上传文档失败: " + e.getMessage());
        }
    }
    
    /**
     * 下载知识库文档
     * 
     * @param id 知识库文档ID
     * @param filename 文件名（可选）
     * @return 文件下载响应
     */
    @GetMapping("/download/{id}")
    public ResponseEntity<InputStreamResource> downloadKnowledge(
            @PathVariable Long id,
            @RequestParam(value = "filename", required = false) String filename) {
        
        log.info("接收到下载请求: ID={}, 指定文件名={}", id, filename);
        
        try {
            // 获取知识库文档内容
            InputStream inputStream = knowledgeUploadService.downloadKnowledge(id);
            
            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            
            // 处理文件名
            String encodedFilename = filename;
            if (encodedFilename == null || encodedFilename.isEmpty()) {
                encodedFilename = "知识文档_" + id;
            }
            encodedFilename = URLEncoder.encode(encodedFilename, StandardCharsets.UTF_8.name())
                    .replaceAll("\\+", "%20");
            
            headers.setContentDispositionFormData("attachment", encodedFilename);
            
            log.info("文档下载成功: ID={}", id);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new InputStreamResource(inputStream));
        } catch (BusinessException e) {
            log.error("下载文档业务异常: code={}, message={}", e.getCode(), e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("下载文档未预期异常: {}", e.getMessage(), e);
            throw new BusinessException(500, "下载文档失败: " + e.getMessage());
        }
    }
} 