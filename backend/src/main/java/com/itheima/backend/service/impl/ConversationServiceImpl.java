package com.itheima.backend.service.impl;

import com.itheima.backend.common.enums.MessageTypeEnum;
import com.itheima.backend.mapper.ConversationMapper;
import com.itheima.backend.mapper.MessageMapper;
import com.itheima.backend.model.dto.CreateConversationDTO;
import com.itheima.backend.model.dto.SendMessageDTO;
import com.itheima.backend.model.entity.Conversation;
import com.itheima.backend.model.entity.Message;
import com.itheima.backend.model.vo.ConversationVO;
import com.itheima.backend.model.vo.MessageVO;
import com.itheima.backend.service.AIService;
import com.itheima.backend.service.ConversationService;
import com.itheima.backend.service.ex.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 会话服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {
    
    private final ConversationMapper conversationMapper;
    private final MessageMapper messageMapper;
    private final AIService aiService;

    @Override
    public List<ConversationVO> getAllConversations() {
        log.info("获取所有会话");
        return conversationMapper.findAll().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ConversationVO createConversation(CreateConversationDTO dto) {
        log.info("创建会话: {}", dto);
        
        // 参数校验
        if (!StringUtils.hasText(dto.getTitle())) {
            throw new BusinessException(400, "会话标题不能为空");
        }
        
        Conversation conversation = new Conversation();
        conversation.setTitle(dto.getTitle());
        conversation.setCreatedAt(LocalDateTime.now());
        conversation.setUpdatedAt(LocalDateTime.now());
        
        conversationMapper.insert(conversation);
        return convertToVO(conversation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteConversation(Long id) {
        log.info("删除会话: {}", id);
        
        // 参数校验
        if (id == null) {
            throw new BusinessException(400, "会话ID不能为空");
        }
        
        // 删除会话
        conversationMapper.deleteById(id);
        
        // 删除会话下的所有消息
        messageMapper.deleteByConversationId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageVO sendMessage(Long conversationId, SendMessageDTO dto) {
        log.info("发送消息: conversationId={}, content={}", conversationId, dto.getContent());
        
        // 参数校验
        if (conversationId == null) {
            throw new BusinessException(400, "会话ID不能为空");
        }
        if (!StringUtils.hasText(dto.getContent())) {
            throw new BusinessException(400, "消息内容不能为空");
        }
        
        // 保存用户消息
        Message userMessage = new Message();
        userMessage.setContent(dto.getContent());
        userMessage.setType(MessageTypeEnum.USER);
        userMessage.setRole("user");
        userMessage.setCreatedAt(LocalDateTime.now());
        userMessage.setConversationId(conversationId);
        messageMapper.insert(userMessage);

        // 获取对话历史
        List<Message> history = messageMapper.findByConversationId(conversationId);

        // 获取AI响应
        MessageVO aiResponse = aiService.sendMessage(conversationId, dto.getContent());
        
        // 更新对话的最后一条消息
        Conversation conversation = conversationMapper.findById(conversationId);
        if (conversation != null) {
            conversation.setLastMessage(dto.getContent());
            conversation.setUpdatedAt(LocalDateTime.now());
            conversationMapper.update(conversation);
        }

        return aiResponse;
    }

    @Override
    public List<ConversationVO> searchConversations(String keyword) {
        log.info("搜索会话: {}", keyword);
        
        if (!StringUtils.hasText(keyword)) {
            return getAllConversations();
        }
        
        return conversationMapper.search(keyword).stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public String exportConversation(Long id) {
        log.info("导出会话: {}", id);
        
        // 参数校验
        if (id == null) {
            throw new BusinessException(400, "会话ID不能为空");
        }
        
        Conversation conversation = conversationMapper.findById(id);
        if (conversation == null) {
            throw new BusinessException(400, "会话不存在");
        }

        List<Message> messages = messageMapper.findByConversationId(id);

        StringBuilder export = new StringBuilder();
        export.append("对话标题: ").append(conversation.getTitle()).append("\n\n");
        
        for (Message message : messages) {
            export.append(message.getType() == MessageTypeEnum.USER ? "用户" : "AI助手")
                  .append(": ")
                  .append(message.getContent())
                  .append("\n\n");
        }

        return export.toString();
    }

    /**
     * 将实体转换为VO
     */
    private ConversationVO convertToVO(Conversation conversation) {
        if (conversation == null) {
            return null;
        }
        
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

    /**
     * 将消息实体转换为VO
     */
    private MessageVO convertToMessageVO(Message message) {
        if (message == null) {
            return null;
        }
        
        MessageVO vo = new MessageVO();
        vo.setId(message.getId());
        vo.setRole(message.getRole());
        vo.setContent(message.getContent());
        vo.setCreatedAt(message.getCreatedAt());
        return vo;
    }
} 