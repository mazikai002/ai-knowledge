package com.itheima.backend.model.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

/**
 * 知识库文档ES索引模型
 */
@Data
@Document(indexName = "knowledge")
public class KnowledgeDocument {
    
    /**
     * 文档ID
     */
    @Id
    private Long id;
    
    /**
     * 标题
     */
    @Field(type = FieldType.Text, analyzer = "standard", searchAnalyzer = "standard")
    private String title;
    
    /**
     * 内容
     */
    @Field(type = FieldType.Text, analyzer = "standard", searchAnalyzer = "standard")
    private String content;
    
    /**
     * 文件类型
     */
    @Field(type = FieldType.Keyword)
    private String fileType;
    
    /**
     * 标签
     */
    @Field(type = FieldType.Text, analyzer = "standard", searchAnalyzer = "standard")
    private String tags;
    
    /**
     * 描述
     */
    @Field(type = FieldType.Text, analyzer = "standard", searchAnalyzer = "standard")
    private String description;
    
    /**
     * 创建时间
     */
    @Field(type = FieldType.Date)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @Field(type = FieldType.Date)
    private LocalDateTime updateTime;
    
    /**
     * 创建者ID
     */
    @Field(type = FieldType.Long)
    private Long createBy;
    
    /**
     * 创建者名称
     */
    @Field(type = FieldType.Keyword)
    private String createByName;
} 