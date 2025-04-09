package com.itheima.backend.service.impl;

import com.itheima.backend.common.constant.BusinessConstant;
import com.itheima.backend.service.AIModelService;
import com.itheima.backend.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * AI模型服务实现类
 */
@Slf4j
@Service
public class AIModelServiceImpl implements AIModelService {
    
    @Override
    public String callModel(String model, String content) {
        if (model == null || model.trim().isEmpty()) {
            model = BusinessConstant.DEFAULT_AI_MODEL;
        }
        if (content == null || content.trim().isEmpty()) {
            throw new BusinessException(400, "输入内容不能为空");
        }
        
        // TODO: 实现实际的AI模型调用逻辑
        // 这里需要集成具体的AI模型API，比如OpenAI的API
        log.info("调用AI模型: {}, 输入内容: {}", model, content);
        return "AI模型响应内容";
    }
    
    @Override
    public String callModelWithHistory(String model, String content, String history) {
        if (model == null || model.trim().isEmpty()) {
            model = BusinessConstant.DEFAULT_AI_MODEL;
        }
        if (content == null || content.trim().isEmpty()) {
            throw new BusinessException(400, "输入内容不能为空");
        }
        
        // TODO: 实现带历史消息的AI模型调用逻辑
        // 这里需要集成具体的AI模型API，并处理历史消息
        log.info("调用AI模型: {}, 输入内容: {}, 历史消息: {}", model, content, history);
        return "AI模型响应内容";
    }
} 