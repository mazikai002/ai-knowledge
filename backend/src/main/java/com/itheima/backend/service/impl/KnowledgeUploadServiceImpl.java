package com.itheima.backend.service.impl;

import com.itheima.backend.model.document.KnowledgeDocument;
import com.itheima.backend.model.dto.KnowledgeDTO;
import com.itheima.backend.model.entity.User;
import com.itheima.backend.model.vo.KnowledgeVO;
import com.itheima.backend.repository.mybatis.UserMapper;
import com.itheima.backend.service.DocumentParserService;
import com.itheima.backend.service.KnowledgeSearchService;
import com.itheima.backend.service.KnowledgeUploadService;
import com.itheima.backend.service.StorageService;
import com.itheima.backend.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 知识库文档上传服务实现类
 * 
 * @author developer
 * @date 2025/04/06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KnowledgeUploadServiceImpl implements KnowledgeUploadService {
    
    /**
     * 存储服务
     */
    private final StorageService storageService;
    
    /**
     * 文档解析服务
     */
    private final DocumentParserService documentParserService;
    
    /**
     * 知识搜索服务
     */
    private final KnowledgeSearchService knowledgeSearchService;
    
    /**
     * 用户数据访问接口
     */
    private final UserMapper userMapper;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public KnowledgeVO uploadKnowledge(MultipartFile file, String title, String tags, String description, Long createBy) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(400, "上传文件不能为空");
        }
        
        if (!StringUtils.hasText(title)) {
            // 如果没有提供标题，使用文件名作为标题
            String originalFilename = file.getOriginalFilename();
            if (originalFilename != null && originalFilename.contains(".")) {
                title = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            } else {
                title = "未命名文档";
            }
        }
        
        try {
            // 1. 解析文档内容
            String content = documentParserService.parseContent(file);
            
            // 2. 获取文件类型
            String fileType = documentParserService.getFileType(file);
            
            // 3. 生成文件存储路径
            String objectName = generateObjectName(file.getOriginalFilename());
            
            // 4. 上传文件到MinIO
            String fileUrl = storageService.uploadFile(file, objectName);
            
            // 5. 获取创建者信息
            User user = null;
            if (createBy != null) {
                user = userMapper.findById(createBy);
            }
            
            // 6. 创建知识库VO对象
            KnowledgeVO knowledgeVO = new KnowledgeVO();
            knowledgeVO.setTitle(title);
            knowledgeVO.setContent(content);
            knowledgeVO.setFileType(fileType);
            knowledgeVO.setTags(tags);
            knowledgeVO.setDescription(description);
            knowledgeVO.setCreateTime(LocalDateTime.now());
            knowledgeVO.setUpdateTime(LocalDateTime.now());
            knowledgeVO.setCreateBy(createBy);
            knowledgeVO.setFilePath(objectName);
            knowledgeVO.setFileUrl(fileUrl);
            
            if (user != null) {
                knowledgeVO.setCreateByName(user.getUsername());
            }
            
            // 7. 将文档索引到ElasticSearch
            KnowledgeDocument document = knowledgeSearchService.indexKnowledge(knowledgeVO);
            knowledgeVO.setId(document.getId());
            
            log.info("知识库文档上传成功: {}", knowledgeVO.getTitle());
            return knowledgeVO;
            
        } catch (Exception e) {
            log.error("知识库文档上传失败: {}", e.getMessage(), e);
            throw new BusinessException(500, "知识库文档上传失败: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public KnowledgeVO uploadKnowledge(MultipartFile file, KnowledgeDTO knowledgeDTO) {
        if (knowledgeDTO == null) {
            throw new BusinessException(400, "知识库信息不能为空");
        }
        
        return uploadKnowledge(
                file,
                knowledgeDTO.getTitle(),
                knowledgeDTO.getTags(),
                knowledgeDTO.getDescription(),
                knowledgeDTO.getCreateBy()
        );
    }
    
    @Override
    public InputStream downloadKnowledge(Long id) {
        if (id == null) {
            throw new BusinessException(400, "知识库ID不能为空");
        }
        
        // 1. 从ES中获取知识库文档信息
        KnowledgeVO knowledgeVO = knowledgeSearchService.getKnowledge(id);
        if (knowledgeVO == null) {
            throw new BusinessException(404, "知识库文档不存在");
        }
        
        // 2. 从MinIO中获取文件
        return storageService.getFile(knowledgeVO.getFilePath());
    }
    
    /**
     * 生成对象存储名称
     * 
     * @param originalFilename 原始文件名
     * @return 存储对象名称
     */
    private String generateObjectName(String originalFilename) {
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        
        return "knowledge/" + UUID.randomUUID().toString().replace("-", "") + extension;
    }
} 