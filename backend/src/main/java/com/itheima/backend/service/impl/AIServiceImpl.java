package com.itheima.backend.service.impl;

import com.itheima.backend.model.entity.Conversation;
import com.itheima.backend.model.entity.Message;
import com.itheima.backend.model.vo.MessageVO;
import com.itheima.backend.mapper.ConversationMapper;
import com.itheima.backend.mapper.MessageMapper;
import com.itheima.backend.service.AIService;
import com.itheima.backend.service.ex.BusinessException;
import com.itheima.backend.model.converter.MessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AI服务实现类
 * 实现与AI模型交互的具体业务逻辑
 */
@Slf4j
@Service
public class AIServiceImpl implements AIService {

    @Autowired
    private ConversationMapper conversationMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private AIModelService aiModelService;

    /**
     * 发送消息并获取AI响应
     *
     * @param conversationId 对话ID
     * @param content 消息内容
     * @return AI响应消息
     */
    @Override
    @Transactional
    public MessageVO sendMessage(Long conversationId, String content) {
        // 获取会话
        Conversation conversation = conversationMapper.findById(conversationId);
        if (conversation == null) {
            throw new BusinessException(400, "会话不存在");
        }

        // 保存用户消息
        Message userMessage = new Message();
        userMessage.setConversationId(conversationId);
        userMessage.setRole("user");
        userMessage.setContent(content);
        userMessage.setCreatedAt(LocalDateTime.now());
        messageMapper.insert(userMessage);

        // 获取历史消息
        List<Message> history = messageMapper.findByConversationId(conversationId);

        // 调用AI模型获取回复
        String aiResponse = aiModelService.callModel("gpt-4", content);

        // 保存AI回复
        Message aiMessage = new Message();
        aiMessage.setConversationId(conversationId);
        aiMessage.setRole("assistant");
        aiMessage.setContent(aiResponse);
        aiMessage.setCreatedAt(LocalDateTime.now());
        messageMapper.insert(aiMessage);

        // 更新会话时间
        conversation.setUpdatedAt(LocalDateTime.now());
        conversationMapper.update(conversation);

        return MessageConverter.toVO(aiMessage);
    }

    /**
     * 处理文档并添加到知识库
     *
     * @param fileId 文件ID
     * @return 处理结果
     */
    @Override
    @Transactional
    public String processDocument(Long fileId) {
        // TODO: 实现文档处理逻辑
        log.info("处理文档: {}", fileId);
        return "文档处理成功";
    }

    /**
     * 从知识库中检索相关信息
     *
     * @param query 查询内容
     * @return 相关文档内容
     */
    @Override
    public String retrieveFromKnowledgeBase(String query) {
        // TODO: 实现知识库检索逻辑
        return null;
    }

    /**
     * 删除知识库中的文档
     *
     * @param fileId 文件ID
     * @return 删除结果
     */
    @Override
    @Transactional
    public String deleteDocument(Long fileId) {
        // TODO: 实现文档删除逻辑
        log.info("删除文档: {}", fileId);
        return "文档删除成功";
    }

    @Override
    @Transactional
    public void deleteConversation(Long conversationId) {
        // 删除会话下的所有消息
        messageMapper.deleteByConversationId(conversationId);
        
        // 删除会话
        conversationMapper.deleteById(conversationId);
    }

    @Override
    public List<MessageVO> getConversationHistory(Long conversationId) {
        List<Message> messages = messageMapper.findByConversationId(conversationId);
        return messages.stream()
            .map(MessageConverter::toVO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void processDocument(String title, String content) {
        // TODO: 实现文档处理逻辑
    }
} 