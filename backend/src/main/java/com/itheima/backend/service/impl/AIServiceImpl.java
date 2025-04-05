package com.itheima.backend.service.impl;

import com.itheima.backend.common.constant.BusinessConstant;
import com.itheima.backend.common.enums.MessageTypeEnum;
import com.itheima.backend.model.dto.MessageDTO;
import com.itheima.backend.model.entity.Conversation;
import com.itheima.backend.model.entity.Message;
import com.itheima.backend.model.vo.MessageVO;
import com.itheima.backend.repository.mybatis.ConversationMapper;
import com.itheima.backend.repository.mybatis.MessageMapper;
import com.itheima.backend.service.AIService;
import com.itheima.backend.service.AIModelService;
import com.itheima.backend.service.ex.BusinessException;
import com.itheima.backend.model.converter.MessageConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AI服务实现类
 * 实现与AI模型交互的具体业务逻辑
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {

    private final ConversationMapper conversationMapper;
    private final MessageMapper messageMapper;
    private final AIModelService aiModelService;

    /**
     * 发送消息并获取AI响应
     *
     * @param conversationId 对话ID
     * @param content 消息内容
     * @return AI响应消息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MessageVO sendMessage(Long conversationId, String content) {
        // 参数校验
        if (conversationId == null) {
            throw new BusinessException(400, "会话ID不能为空");
        }
        if (!StringUtils.hasText(content)) {
            throw new BusinessException(400, "消息内容不能为空");
        }
        if (content.length() > BusinessConstant.MAX_MESSAGE_LENGTH) {
            throw new BusinessException(400, "消息内容超出长度限制");
        }

        // 获取会话
        Conversation conversation = conversationMapper.findById(conversationId);
        if (conversation == null) {
            throw new BusinessException(400, "会话不存在");
        }

        // 保存用户消息
        Message userMessage = new Message();
        userMessage.setConversationId(conversationId);
        userMessage.setRole(BusinessConstant.MESSAGE_ROLE_USER);
        userMessage.setType(MessageTypeEnum.USER);
        userMessage.setContent(content);
        userMessage.setCreatedAt(LocalDateTime.now());
        messageMapper.insert(userMessage);

        // 获取历史消息
        List<Message> history = messageMapper.findByConversationId(conversationId);

        // 调用AI模型获取回复
        String aiResponse = aiModelService.callModel(BusinessConstant.DEFAULT_AI_MODEL, content);

        // 保存AI回复
        Message aiMessage = new Message();
        aiMessage.setConversationId(conversationId);
        aiMessage.setRole(BusinessConstant.MESSAGE_ROLE_ASSISTANT);
        aiMessage.setType(MessageTypeEnum.ASSISTANT);
        aiMessage.setContent(aiResponse);
        aiMessage.setCreatedAt(LocalDateTime.now());
        messageMapper.insert(aiMessage);

        // 更新会话最后一条消息
        conversation.setLastMessage(content);
        conversation.setUpdatedAt(LocalDateTime.now());
        conversationMapper.update(conversation);

        // 返回AI回复消息
        return MessageConverter.toVO(aiMessage);
    }

    /**
     * 处理文档并添加到知识库
     *
     * @param title 文档标题
     * @param content 文档内容
     * @param fileType 文件类型
     * @return 处理结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String processDocument(String title, String content, String fileType) {
        // 参数校验
        if (!StringUtils.hasText(title)) {
            throw new BusinessException(400, "文档标题不能为空");
        }
        if (!StringUtils.hasText(content)) {
            throw new BusinessException(400, "文档内容不能为空");
        }
        if (!StringUtils.hasText(fileType)) {
            throw new BusinessException(400, "文件类型不能为空");
        }
        if (title.length() > BusinessConstant.MAX_TITLE_LENGTH) {
            throw new BusinessException(400, "文档标题超出长度限制");
        }
        if (content.length() > BusinessConstant.MAX_CONTENT_LENGTH) {
            throw new BusinessException(400, "文档内容超出长度限制");
        }

        // TODO: 实现文档处理逻辑
        // 1. 根据文件类型选择不同的处理方式
        // 2. 提取文档内容的关键信息
        // 3. 将处理后的内容存储到知识库
        // 4. 建立文档索引
        log.info("处理文档: 标题={}, 类型={}, 内容长度={}", title, fileType, content.length());
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
        if (!StringUtils.hasText(query)) {
            throw new BusinessException(400, "查询内容不能为空");
        }

        // TODO: 实现知识库检索逻辑
        // 1. 对查询内容进行预处理
        // 2. 在知识库中搜索相关内容
        // 3. 对结果进行排序和过滤
        // 4. 返回最相关的内容
        log.info("检索知识库: {}", query);
        return "检索结果";
    }

    /**
     * 删除知识库中的文档
     *
     * @param fileId 文件ID
     * @return 删除结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteDocument(Long fileId) {
        if (fileId == null) {
            throw new BusinessException(400, "文件ID不能为空");
        }

        // TODO: 实现文档删除逻辑
        // 1. 检查文件是否存在
        // 2. 删除文件内容
        // 3. 删除相关索引
        // 4. 更新知识库状态
        log.info("删除文档: {}", fileId);
        return "文档删除成功";
    }

    /**
     * 删除会话
     *
     * @param conversationId 会话ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteConversation(Long conversationId) {
        if (conversationId == null) {
            throw new BusinessException(400, "会话ID不能为空");
        }

        // 删除会话下的所有消息
        messageMapper.deleteByConversationId(conversationId);
        
        // 删除会话
        conversationMapper.deleteById(conversationId);
    }

    /**
     * 获取会话历史记录
     *
     * @param conversationId 会话ID
     * @return 消息列表
     */
    @Override
    public List<MessageVO> getConversationHistory(Long conversationId) {
        if (conversationId == null) {
            throw new BusinessException(400, "会话ID不能为空");
        }

        List<Message> messages = messageMapper.findByConversationId(conversationId);
        return messages.stream()
                .map(MessageConverter::toVO)
                .collect(Collectors.toList());
    }
} 