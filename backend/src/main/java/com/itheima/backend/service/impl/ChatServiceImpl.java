package com.itheima.backend.service.impl;

import com.itheima.backend.model.dto.ChatMessageDTO;
import com.itheima.backend.model.dto.ChatMessageItemDTO;
import com.itheima.backend.model.vo.ChatChoiceVO;
import com.itheima.backend.model.vo.ChatCompletionVO;
import com.itheima.backend.model.vo.ChatMessageVO;
import com.itheima.backend.model.vo.ChatUsageVO;
import com.itheima.backend.service.ChatService;
import com.itheima.backend.service.ex.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * 聊天服务实现类
 * 
 * @author developer
 * @date 2025/04/06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    
    @Value("${ai.default-model}")
    private String defaultModel;
    
    /**
     * 生成聊天完成响应
     *
     * @param messageDTO 聊天消息DTO
     * @return 聊天完成响应
     */
    @Override
    public ChatCompletionVO generateChatCompletion(ChatMessageDTO messageDTO) {
        // 参数校验
        if (messageDTO == null) {
            throw new BusinessException(400, "请求参数不能为空");
        }
        
        List<ChatMessageItemDTO> messages = messageDTO.getMessages();
        if (messages == null || messages.isEmpty()) {
            throw new BusinessException(400, "消息列表不能为空");
        }
        
        // 设置默认值
        String model = messageDTO.getModel();
        if (!StringUtils.hasText(model)) {
            model = defaultModel;
            messageDTO.setModel(model);
        }
        
        Float temperature = messageDTO.getTemperature();
        if (temperature == null) {
            temperature = 0.7f;
            messageDTO.setTemperature(temperature);
        }
        
        Integer maxTokens = messageDTO.getMaxTokens();
        if (maxTokens == null) {
            maxTokens = 2000;
            messageDTO.setMaxTokens(maxTokens);
        }
        
        // TODO: 调用实际的AI模型API，目前使用模拟数据
        log.info("调用AI模型: model={}, temperature={}, maxTokens={}, useKnowledgeBase={}", 
                model, temperature, maxTokens, messageDTO.getUseKnowledgeBase());
        
        // 构建响应
        ChatCompletionVO completionVO = new ChatCompletionVO();
        completionVO.setId(UUID.randomUUID().toString().replace("-", ""));
        completionVO.setObject("chat.completion");
        completionVO.setCreated(Instant.now().getEpochSecond());
        completionVO.setModel(model);
        
        // 构建消息
        ChatMessageVO messageVO = new ChatMessageVO();
        messageVO.setRole("assistant");
        messageVO.setContent("这是来自AI助手的回复。在实际实现中，这里应该是调用AI模型API得到的回复内容。");
        
        // 构建选择
        ChatChoiceVO choiceVO = new ChatChoiceVO();
        choiceVO.setIndex(0);
        choiceVO.setMessage(messageVO);
        choiceVO.setFinishReason("stop");
        
        completionVO.setChoices(Collections.singletonList(choiceVO));
        
        // 设置使用统计
        ChatUsageVO usageVO = new ChatUsageVO();
        usageVO.setPromptTokens(100);
        usageVO.setCompletionTokens(50);
        usageVO.setTotalTokens(150);
        completionVO.setUsage(usageVO);
        
        return completionVO;
    }
} 