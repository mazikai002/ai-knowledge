package com.itheima.backend.model.dto;

import lombok.Data;

/**
 * 聊天消息项DTO
 * 
 * @author developer
 * @date 2025/04/06
 */
@Data
public class ChatMessageItemDTO {
    
    /**
     * 消息角色：system、user、assistant
     */
    private String role;
    
    /**
     * 消息内容
     */
    private String content;
} 