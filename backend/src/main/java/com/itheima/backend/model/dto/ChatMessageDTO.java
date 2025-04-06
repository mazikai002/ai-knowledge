package com.itheima.backend.model.dto;

import lombok.Data;
import java.util.List;

/**
 * 聊天消息请求DTO
 * 
 * @author developer
 * @date 2025/04/06
 */
@Data
public class ChatMessageDTO {
    
    /**
     * AI模型名称
     */
    private String model;
    
    /**
     * 消息列表
     */
    private List<ChatMessageItemDTO> messages;
    
    /**
     * 温度参数，控制输出的随机性
     */
    private Float temperature;
    
    /**
     * 最大输出令牌数
     */
    private Integer maxTokens;
    
    /**
     * 是否使用知识库增强
     */
    private Boolean useKnowledgeBase;
} 