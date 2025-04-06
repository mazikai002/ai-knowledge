package com.itheima.backend.service;

import com.itheima.backend.model.dto.ChatMessageDTO;
import com.itheima.backend.model.vo.ChatCompletionVO;

/**
 * 聊天服务接口
 * 
 * @author developer
 * @date 2025/04/06
 */
public interface ChatService {
    
    /**
     * 生成聊天完成响应
     * 
     * @param messageDTO 聊天消息DTO
     * @return 聊天完成响应
     */
    ChatCompletionVO generateChatCompletion(ChatMessageDTO messageDTO);
} 