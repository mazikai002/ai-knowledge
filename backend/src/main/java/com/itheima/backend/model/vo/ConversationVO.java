package com.itheima.backend.model.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 会话视图对象
 */
@Data
public class ConversationVO {
    private Long id;
    private String title;
    private String lastMessage;
    private LocalDateTime updatedAt;
    private List<MessageVO> messages;
}