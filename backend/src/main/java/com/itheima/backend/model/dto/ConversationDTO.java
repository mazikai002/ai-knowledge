package com.itheima.backend.model.dto;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 会话数据传输对象
 */
@Data
public class ConversationDTO {
    
    @NotEmpty(message = "标题不能为空")
    @Size(max = 100, message = "标题长度不能超过100个字符")
    private String title;
} 