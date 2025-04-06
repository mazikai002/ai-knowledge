package com.itheima.backend.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 知识库VO
 */
@Data
public class KnowledgeVO {
    
    /**
     * 主键ID
     */
    private Long id;
    
    /**
     * 标题
     */
    private String title;
    
    /**
     * 内容
     */
    private String content;
    
    /**
     * 文件类型
     */
    private String fileType;
    
    /**
     * 标签
     */
    private String tags;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 文件路径（MinIO对象存储路径）
     */
    private String filePath;
    
    /**
     * 文件URL
     */
    private String fileUrl;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 创建者ID
     */
    private Long createBy;
    
    /**
     * 创建者名称
     */
    private String createByName;
} 