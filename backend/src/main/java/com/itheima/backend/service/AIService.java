package com.itheima.backend.service;

import com.itheima.backend.model.vo.MessageVO;
import java.util.List;

/**
 * AI服务接口
 */
public interface AIService {
    
    /**
     * 发送消息并获取AI回复
     *
     * @param conversationId 会话ID
     * @param content 消息内容
     * @return AI回复消息
     */
    MessageVO sendMessage(Long conversationId, String content);
    
    /**
     * 删除会话
     *
     * @param conversationId 会话ID
     */
    void deleteConversation(Long conversationId);
    
    /**
     * 获取会话历史记录
     *
     * @param conversationId 会话ID
     * @return 消息列表
     */
    List<MessageVO> getConversationHistory(Long conversationId);
    
    /**
     * 处理文档
     *
     * @param title 文档标题
     * @param content 文档内容
     */
    void processDocument(String title, String content);
    
    /**
     * 从知识库中检索信息
     *
     * @param query 查询内容
     * @return 检索结果
     */
    String retrieveFromKnowledgeBase(String query);
    
    /**
     * 删除文档
     *
     * @param documentId 文档ID
     */
    void deleteDocument(Long documentId);
} 