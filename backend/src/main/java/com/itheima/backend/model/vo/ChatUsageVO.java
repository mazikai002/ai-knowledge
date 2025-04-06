package com.itheima.backend.model.vo;

import lombok.Data;

/**
 * 聊天资源使用VO
 * 
 * @author developer
 * @date 2025/04/06
 */
@Data
public class ChatUsageVO {
    
    /**
     * 提示令牌数
     */
    private Integer promptTokens;
    
    /**
     * 完成令牌数
     */
    private Integer completionTokens;
    
    /**
     * 总令牌数
     */
    private Integer totalTokens;
} 