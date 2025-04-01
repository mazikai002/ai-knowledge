package com.itheima.backend.model.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ConversationVO {
    private Long id;
    private String title;
    private String lastMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<MessageVO> messages;
}