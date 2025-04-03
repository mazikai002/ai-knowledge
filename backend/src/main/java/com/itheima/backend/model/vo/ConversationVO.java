package com.itheima.backend.model.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 会话视图对象
 */
@Data
public class ConversationVO {
    /**
     * 会话ID
     */
    private Long id;
    
    /**
     * 会话标题
     */
    private String title;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
    
    private String lastMessage;
    private List<MessageVO> messages;
}