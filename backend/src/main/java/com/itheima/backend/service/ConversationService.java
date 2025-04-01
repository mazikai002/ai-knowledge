package com.itheima.backend.service;

import com.itheima.backend.model.dto.CreateConversationDTO;
import com.itheima.backend.model.dto.SendMessageDTO;
import com.itheima.backend.model.vo.ConversationVO;
import com.itheima.backend.model.vo.MessageVO;

import java.util.List;

public interface ConversationService {
    List<ConversationVO> getAllConversations();
    
    ConversationVO createConversation(CreateConversationDTO dto);
    
    void deleteConversation(Long id);
    
    MessageVO sendMessage(Long conversationId, SendMessageDTO dto);
    
    List<ConversationVO> searchConversations(String keyword);
    
    String exportConversation(Long id);
} 