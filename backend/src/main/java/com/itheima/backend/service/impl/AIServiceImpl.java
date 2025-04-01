package com.itheima.backend.service.impl;

import com.itheima.backend.entity.Conversation;
import com.itheima.backend.entity.Message;
import com.itheima.backend.repository.ConversationRepository;
import com.itheima.backend.repository.MessageRepository;
import com.itheima.backend.service.AIService;
import com.itheima.backend.service.AIModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AI服务实现类
 * 实现与AI模型交互的具体业务逻辑
 */
@Slf4j
@Service
public class AIServiceImpl implements AIService {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AIModelService aiModelService;

    /**
     * 发送消息并获取AI响应
     *
     * @param conversationId 对话ID
     * @param content 消息内容
     * @param model 使用的AI模型
     * @return AI响应消息
     */
    @Override
    @Transactional
    public Message sendMessage(Long conversationId, String content, String model) {
        try {
            // 查找对话
            Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("未找到对话"));

            // 保存用户消息
            Message userMessage = new Message();
            userMessage.setConversationId(conversationId);
            userMessage.setContent(content);
            userMessage.setRole("user");
            userMessage.setTimestamp(LocalDateTime.now());
            messageRepository.save(userMessage);

            // 调用AI模型获取响应
            String aiResponse = aiModelService.callModel(model, content);

            // 保存AI响应
            Message aiMessage = new Message();
            aiMessage.setConversationId(conversationId);
            aiMessage.setContent(aiResponse);
            aiMessage.setRole("assistant");
            aiMessage.setTimestamp(LocalDateTime.now());
            messageRepository.save(aiMessage);

            // 更新对话的最后消息和时间戳
            conversation.setLastMessage(content);
            conversation.setTimestamp(LocalDateTime.now());
            conversationRepository.save(conversation);

            return aiMessage;
        } catch (Exception e) {
            log.error("发送消息时发生错误: {}", e.getMessage(), e);
            throw new RuntimeException("发送消息失败", e);
        }
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
        log.info("从知识库检索: {}", query);
        return "检索结果";
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
} 