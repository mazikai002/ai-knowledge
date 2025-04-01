package com.itheima.backend.model.dto;

import lombok.Data;
import javax.validation.constraints.NotEmpty;

/**
 * 消息数据传输对象
 */
@Data
public class MessageDTO {
    
    @NotEmpty(message = "内容不能为空")
    private String content;
} 