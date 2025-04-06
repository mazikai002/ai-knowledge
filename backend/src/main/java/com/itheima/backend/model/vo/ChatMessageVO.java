package com.itheima.backend.model.vo;

import lombok.Data;

/**
 * 聊天消息VO
 * 
 * @author developer
 * @date 2025/04/06
 */
@Data
public class ChatMessageVO {
    
    /**
     * 角色：system、user、assistant
     */
    private String role;
    
    /**
     * 消息内容
     */
    private String content;
} 