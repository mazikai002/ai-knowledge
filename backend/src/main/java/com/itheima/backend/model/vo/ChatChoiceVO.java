package com.itheima.backend.model.vo;

import lombok.Data;

/**
 * 聊天选择VO
 * 
 * @author developer
 * @date 2025/04/06
 */
@Data
public class ChatChoiceVO {
    
    /**
     * 索引
     */
    private Integer index;
    
    /**
     * 消息
     */
    private ChatMessageVO message;
    
    /**
     * 结束原因
     */
    private String finishReason;
} 