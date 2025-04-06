package com.itheima.backend.model.vo;

import lombok.Data;
import java.util.List;

/**
 * 聊天完成响应VO
 * 
 * @author developer
 * @date 2025/04/06
 */
@Data
public class ChatCompletionVO {
    
    /**
     * 响应ID
     */
    private String id;
    
    /**
     * 响应对象类型
     */
    private String object;
    
    /**
     * 创建时间戳
     */
    private Long created;
    
    /**
     * 模型名称
     */
    private String model;
    
    /**
     * 选择列表
     */
    private List<ChatChoiceVO> choices;
    
    /**
     * 使用情况统计
     */
    private ChatUsageVO usage;
} 