package com.itheima.backend.service.impl;

import com.itheima.backend.mapper.ConversationMapper;
import com.itheima.backend.mapper.MessageMapper;
import com.itheima.backend.mapper.MessageMapper;
import com.itheima.backend.model.dto.CreateConversationDTO;
import com.itheima.backend.model.dto.SendMessageDTO;
import com.itheima.backend.model.entity.Conversation;
import com.itheima.backend.model.entity.Message;
import com.itheima.backend.model.vo.ConversationVO;
import com.itheima.backend.model.vo.MessageVO;
import com.itheima.backend.service.AIService;
import com.itheima.backend.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversationServiceImpl implements ConversationService {
    @Autowired
    private ConversationMapper conversationMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private AIService aiService;

    @Override
    public List<ConversationVO> getAllConversations() {
        return conversationMapper.findAll().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ConversationVO createConversation(CreateConversationDTO dto) {
        Conversation conversation = new Conversation();
        conversation.setTitle(dto.getTitle());
        conversation.setCreatedAt(LocalDateTime.now());
        conversation.setUpdatedAt(LocalDateTime.now());
        
        conversationMapper.insert(conversation);
        return convertToVO(conversation);
    }

    @Override
    @Transactional
    public void deleteConversation(Long id) {
        conversationMapper.deleteById(id);
    }

    @Override
    @Transactional
    public MessageVO sendMessage(Long conversationId, SendMessageDTO dto) {
        // 保存用户消息
        Message userMessage = new Message();
        userMessage.setContent(dto.getContent());
        userMessage.setType(Message.MessageType.USER);
        userMessage.setModel(dto.getModel());
        userMessage.setCreatedAt(LocalDateTime.now());
        userMessage.setConversationId(conversationId);
        messageMapper.insert(userMessage);

        // 获取对话历史
        List<Message> history = messageMapper.findByConversationId(conversationId);

        // 获取AI响应
        Message aiMessage = aiService.generateResponse(dto.getContent(), dto.getModel(), history);
        aiMessage.setConversationId(conversationId);
        messageMapper.insert(aiMessage);

        // 更新对话的最后一条消息
        Conversation conversation = new Conversation();
        conversation.setId(conversationId);
        conversation.setLastMessage(dto.getContent());
        conversation.setUpdatedAt(LocalDateTime.now());
        conversationMapper.updateLastMessage(conversation);

        return convertToMessageVO(aiMessage);
    }

    @Override
    public List<ConversationVO> searchConversations(String keyword) {
        return conversationMapper.search(keyword).stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public String exportConversation(Long id) {
        Conversation conversation = conversationMapper.findAll().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        List<Message> messages = messageMapper.findByConversationId(id);

        StringBuilder export = new StringBuilder();
        export.append("对话标题: ").append(conversation.getTitle()).append("\n\n");
        
        for (Message message : messages) {
            export.append(message.getType() == Message.MessageType.USER ? "用户" : "AI")
                  .append(" (").append(message.getModel()).append("): ")
                  .append(message.getContent())
                  .append("\n\n");
        }

        return export.toString();
    }

    private ConversationVO convertToVO(Conversation conversation) {
        ConversationVO vo = new ConversationVO();
        vo.setId(conversation.getId());
        vo.setTitle(conversation.getTitle());
        vo.setLastMessage(conversation.getLastMessage());
        vo.setCreatedAt(conversation.getCreatedAt());
        vo.setUpdatedAt(conversation.getUpdatedAt());
        
        List<Message> messages = messageMapper.findByConversationId(conversation.getId());
        vo.setMessages(messages.stream()
                .map(this::convertToMessageVO)
                .collect(Collectors.toList()));
        
        return vo;
    }

    private MessageVO convertToMessageVO(Message message) {
        MessageVO vo = new MessageVO();
        vo.setId(message.getId());
        vo.setContent(message.getContent());
        vo.setType(message.getType().name());
        vo.setModel(message.getModel());
        vo.setCreatedAt(message.getCreatedAt());
        return vo;
    }
} 