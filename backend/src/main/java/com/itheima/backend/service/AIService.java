package com.itheima.backend.service;

import com.itheima.backend.model.vo.MessageVO;
import java.util.List;

/**
 * AI服务接口
 */
public interface AIService {
    
    /**
     * 发送消息并获取AI响应
     *
     * @param conversationId 对话ID
     * @param content 消息内容
     * @return AI响应消息
     */
    MessageVO sendMessage(Long conversationId, String content);
    
    /**
     * 处理文档并添加到知识库
     *
     * @param title 文档标题
     * @param content 文档内容
     * @param fileType 文件类型
     * @return 处理结果
     */
    String processDocument(String title, String content, String fileType);
    
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
} 