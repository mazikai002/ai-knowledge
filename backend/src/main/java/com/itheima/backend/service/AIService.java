package com.itheima.backend.service;

import com.itheima.backend.model.Message;
import com.itheima.backend.model.Conversation;

/**
 * AI服务接口
 * 提供与AI模型交互的核心功能
 */
public interface AIService {
    /**
     * 发送消息并获取AI响应
     *
     * @param conversationId 对话ID
     * @param content 消息内容
     * @param model 使用的AI模型
     * @return AI响应消息
     */
    Message sendMessage(Long conversationId, String content, String model);

    /**
     * 处理文档并添加到知识库
     *
     * @param fileId 文件ID
     * @return 处理结果
     */
    String processDocument(Long fileId);

    /**
     * 从知识库中检索相关信息
     *
     * @param query 查询内容
     * @return 相关文档内容
     */
    String retrieveFromKnowledgeBase(String query);

    /**
     * 删除知识库中的文档
     *
     * @param fileId 文件ID
     * @return 删除结果
     */
    String deleteDocument(Long fileId);
} 