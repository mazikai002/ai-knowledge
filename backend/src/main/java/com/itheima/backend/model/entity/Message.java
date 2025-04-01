package com.itheima.backend.model.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Message {
    private Long id;
    private String content;
    private MessageType type;
    private String model;
    private LocalDateTime createdAt;
    private Long conversationId;

    public enum MessageType {
        USER, AI
    }
} 