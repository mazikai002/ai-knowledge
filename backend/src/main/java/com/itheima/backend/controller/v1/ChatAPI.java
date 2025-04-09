package com.itheima.backend.controller.v1;

import com.itheima.backend.common.Result;
import com.itheima.backend.model.dto.ChatMessageDTO;
import com.itheima.backend.model.vo.ChatCompletionVO;
import com.itheima.backend.service.ChatService;
import com.itheima.backend.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 聊天API (V1)
 * 
 * @author developer
 * @date 2025/04/06
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatAPI {
    
    private final ChatService chatService;
    
    /**
     * 发送聊天消息
     * 
     * @param messageDTO 聊天消息DTO
     * @return 聊天完成响应
     */
    @PostMapping("/completions")
    public Result<ChatCompletionVO> chatCompletions(@RequestBody ChatMessageDTO messageDTO) {
        
        log.info("接收到聊天请求: 模型={}, 消息长度={}", 
                messageDTO.getModel(), 
                messageDTO.getMessages() != null ? messageDTO.getMessages().size() : 0);
        
        try {
            ChatCompletionVO completionVO = chatService.generateChatCompletion(messageDTO);
            log.info("聊天响应生成成功: 模型={}, 内容长度={}", 
                    completionVO.getModel(), 
                    completionVO.getChoices() != null && !completionVO.getChoices().isEmpty() ? 
                            completionVO.getChoices().get(0).getMessage().getContent().length() : 0);
            return Result.success(completionVO);
        } catch (BusinessException e) {
            log.error("聊天业务异常: code={}, message={}", e.getCode(), e.getMessage());
            return Result.error(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("聊天未预期异常: {}", e.getMessage(), e);
            return Result.error(500, "生成聊天响应失败: " + e.getMessage());
        }
    }
} 