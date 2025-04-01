package com.itheima.backend.model.entity;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 会话实体类
 */
@Data
@Entity
@Table(name = "conversation")
public class Conversation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "标题不能为空")
    @Size(max = 100, message = "标题长度不能超过100个字符")
    private String title;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    private String lastMessage;
    
    private List<Message> messages;
} 