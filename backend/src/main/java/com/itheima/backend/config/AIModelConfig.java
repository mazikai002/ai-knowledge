package com.itheima.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

/**
 * AI模型配置类
 * 用于配置不同AI模型的参数和API密钥
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ai")
public class AIModelConfig {
    /**
     * OpenAI API密钥
     */
    private String openaiApiKey;
    
    /**
     * Anthropic API密钥
     */
    private String anthropicApiKey;
    
    /**
     * Google API密钥
     */
    private String googleApiKey;
    
    /**
     * 默认使用的AI模型
     */
    private String defaultModel = "gpt-4";
    
    /**
     * GPT-4模型配置
     */
    private ModelConfig gpt4;
    
    /**
     * GPT-3.5模型配置
     */
    private ModelConfig gpt35;
    
    /**
     * Claude-3模型配置
     */
    private ModelConfig claude3;
    
    /**
     * Gemini模型配置
     */
    private ModelConfig gemini;
    
    /**
     * Llama-2模型配置
     */
    private ModelConfig llama2;
    
    /**
     * Qwen模型配置
     */
    private ModelConfig qwen;
    
    /**
     * 模型配置内部类
     * 包含每个模型的具体配置参数
     */
    @Data
    public static class ModelConfig {
        /**
         * API密钥
         */
        private String apiKey;
        
        /**
         * API端点URL
         */
        private String endpoint;
        
        /**
         * 模型名称
         */
        private String model;
        
        /**
         * 最大token数
         */
        private Integer maxTokens;
        
        /**
         * 温度参数（控制输出的随机性）
         */
        private Double temperature;
    }
} 