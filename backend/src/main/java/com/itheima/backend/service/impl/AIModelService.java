package com.itheima.backend.service.impl;

import com.itheima.backend.config.AIModelConfig;
import com.itheima.backend.config.AIModelConfig.ModelConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * AI模型服务类
 * 负责与不同的AI模型进行交互
 */
@Slf4j
@Service
public class AIModelService {

    @Autowired
    private AIModelConfig aiConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 调用指定的AI模型
     *
     * @param model 模型名称
     * @param content 输入内容
     * @return AI模型的响应
     */
    public String callModel(String model, String content) {
        ModelConfig config = getModelConfig(model);
        if (config == null) {
            throw new IllegalArgumentException("不支持的模型: " + model);
        }

        try {
            switch (model) {
                case "gpt-4":
                case "gpt-3.5-turbo":
                    return callOpenAI(config, content);
                case "claude-3":
                    return callAnthropic(config, content);
                case "gemini-pro":
                    return callGoogle(config, content);
                case "llama-2":
                    return callLlama(config, content);
                case "qwen":
                    return callQwen(config, content);
                default:
                    throw new IllegalArgumentException("不支持的模型: " + model);
            }
        } catch (Exception e) {
            log.error("调用AI模型时发生错误: {}", e.getMessage(), e);
            throw new RuntimeException("获取AI响应失败", e);
        }
    }

    /**
     * 获取指定模型的配置
     *
     * @param model 模型名称
     * @return 模型配置
     */
    private ModelConfig getModelConfig(String model) {
        switch (model) {
            case "gpt-4":
                return aiConfig.getGpt4();
            case "gpt-3.5-turbo":
                return aiConfig.getGpt35();
            case "claude-3":
                return aiConfig.getClaude3();
            case "gemini-pro":
                return aiConfig.getGemini();
            case "llama-2":
                return aiConfig.getLlama2();
            case "qwen":
                return aiConfig.getQwen();
            default:
                return null;
        }
    }

    /**
     * 调用OpenAI API
     *
     * @param config 模型配置
     * @param content 输入内容
     * @return OpenAI的响应
     */
    private String callOpenAI(ModelConfig config, String content) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(config.getApiKey());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", config.getModel());
        requestBody.put("messages", new Object[]{
            new HashMap<String, String>() {{
                put("role", "user");
                put("content", content);
            }}
        });
        requestBody.put("max_tokens", config.getMaxTokens());
        requestBody.put("temperature", config.getTemperature());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        Map<String, Object> response = restTemplate.postForObject(
            config.getEndpoint(),
            request,
            Map.class
        );

        return extractOpenAIResponse(response);
    }

    /**
     * 调用Anthropic API
     *
     * @param config 模型配置
     * @param content 输入内容
     * @return Anthropic的响应
     */
    private String callAnthropic(ModelConfig config, String content) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", config.getApiKey());

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", config.getModel());
        requestBody.put("messages", new Object[]{
            new HashMap<String, String>() {{
                put("role", "user");
                put("content", content);
            }}
        });
        requestBody.put("max_tokens", config.getMaxTokens());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        Map<String, Object> response = restTemplate.postForObject(
            config.getEndpoint(),
            request,
            Map.class
        );

        return extractAnthropicResponse(response);
    }

    /**
     * 调用Google AI API
     *
     * @param config 模型配置
     * @param content 输入内容
     * @return Google AI的响应
     */
    private String callGoogle(ModelConfig config, String content) {
        // TODO: 实现Google AI调用
        return "Google AI响应（未实现）";
    }

    /**
     * 调用Llama API
     *
     * @param config 模型配置
     * @param content 输入内容
     * @return Llama的响应
     */
    private String callLlama(ModelConfig config, String content) {
        // TODO: 实现Llama调用
        return "Llama响应（未实现）";
    }

    /**
     * 调用Qwen API
     *
     * @param config 模型配置
     * @param content 输入内容
     * @return Qwen的响应
     */
    private String callQwen(ModelConfig config, String content) {
        // TODO: 实现Qwen调用
        return "Qwen响应（未实现）";
    }

    /**
     * 从OpenAI响应中提取内容
     *
     * @param response API响应
     * @return 提取的内容
     */
    private String extractOpenAIResponse(Map<String, Object> response) {
        try {
            return (String) ((Map<String, Object>) ((Object[]) response.get("choices"))[0])
                .get("message")
                .get("content");
        } catch (Exception e) {
            log.error("提取OpenAI响应时发生错误: {}", e.getMessage());
            throw new RuntimeException("提取OpenAI响应失败");
        }
    }

    /**
     * 从Anthropic响应中提取内容
     *
     * @param response API响应
     * @return 提取的内容
     */
    private String extractAnthropicResponse(Map<String, Object> response) {
        try {
            return (String) ((Object[]) response.get("content"))[0]
                .get("text");
        } catch (Exception e) {
            log.error("提取Anthropic响应时发生错误: {}", e.getMessage());
            throw new RuntimeException("提取Anthropic响应失败");
        }
    }
} 