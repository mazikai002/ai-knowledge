package com.itheima.backend.model.dto;

import com.itheima.backend.common.constant.BusinessConstant;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 知识库DTO
 * 
 * @author developer
 * @date 2025/04/06
 */
@Data
public class KnowledgeDTO {
    
    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
    @Size(max = BusinessConstant.MAX_TITLE_LENGTH, message = "标题长度超出限制")
    private String title;
    
    /**
     * 内容
     */
    @NotBlank(message = "内容不能为空")
    @Size(max = BusinessConstant.MAX_CONTENT_LENGTH, message = "内容长度超出限制")
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
     * 创建人ID
     */
    private Long createBy;
} 