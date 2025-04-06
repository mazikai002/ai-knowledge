package com.itheima.backend.service;

import com.itheima.backend.model.dto.KnowledgeDTO;
import com.itheima.backend.model.vo.KnowledgeVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 知识库文档上传服务接口
 */
public interface KnowledgeUploadService {
    
    /**
     * 上传知识库文档
     * 
     * @param file 文档文件
     * @param title 标题
     * @param tags 标签（逗号分隔）
     * @param description 描述
     * @param createBy 创建者ID
     * @return 知识库文档VO
     */
    KnowledgeVO uploadKnowledge(MultipartFile file, String title, String tags, String description, Long createBy);
    
    /**
     * 上传知识库文档（DTO方式）
     * 
     * @param file 文档文件
     * @param knowledgeDTO 知识库文档DTO
     * @return 知识库文档VO
     */
    KnowledgeVO uploadKnowledge(MultipartFile file, KnowledgeDTO knowledgeDTO);
    
    /**
     * 下载知识库文档
     * 
     * @param id 知识库文档ID
     * @return 文件输入流
     */
    InputStream downloadKnowledge(Long id);
} 