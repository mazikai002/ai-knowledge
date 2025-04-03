package com.itheima.backend.model.entity;

import com.itheima.backend.common.enums.MessageTypeEnum;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * 消息实体类
 */
@Data
@Entity
@Table(name = "message")
public class Message {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "conversation_id", insertable = false, updatable = false)
    private Long conversationId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;
    
    @NotEmpty(message = "角色不能为空")
    private String role;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private MessageTypeEnum type;
    
    @NotEmpty(message = "内容不能为空")
    @Column(columnDefinition = "TEXT")
    private String content;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
} 