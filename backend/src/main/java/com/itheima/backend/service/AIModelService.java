package com.itheima.backend.service;

/**
 * AI模型服务接口
 */
public interface AIModelService {
    
    /**
     * 调用AI模型
     *
     * @param model 模型名称
     * @param content 输入内容
     * @return AI响应内容
     */
    String callModel(String model, String content);
    
    /**
     * 调用AI模型（带历史消息）
     *
     * @param model 模型名称
     * @param content 输入内容
     * @param history 历史消息
     * @return AI响应内容
     */
    String callModelWithHistory(String model, String content, String history);
} 