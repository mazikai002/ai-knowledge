package com.itheima.backend.model.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessageVO {
    private Long id;
    private String content;
    private String type;
    private String model;
    private LocalDateTime createdAt;
} 