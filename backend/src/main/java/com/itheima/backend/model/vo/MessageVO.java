package com.itheima.backend.model.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 消息视图对象
 */
@Data
public class MessageVO {
    
    private Long id;
    private String role;
    private String content;
    private LocalDateTime createdAt;
} 