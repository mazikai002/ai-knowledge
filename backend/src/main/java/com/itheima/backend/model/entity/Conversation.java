package com.itheima.backend.model.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Conversation {
    private Long id;
    private String title;
    private String lastMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Message> messages;
} 