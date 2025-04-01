package com.itheima.backend.model.converter;

import com.itheima.backend.model.dto.MessageDTO;
import com.itheima.backend.model.entity.Message;
import com.itheima.backend.model.vo.MessageVO;
import org.springframework.beans.BeanUtils;

/**
 * 消息对象转换器
 */
public class MessageConverter {
    
    /**
     * DTO 转 Entity
     */
    public static Message toEntity(MessageDTO dto, Long conversationId) {
        if (dto == null) {
            return null;
        }
        Message entity = new Message();
        BeanUtils.copyProperties(dto, entity);
        entity.setConversationId(conversationId);
        return entity;
    }
    
    /**
     * Entity 转 VO
     */
    public static MessageVO toVO(Message entity) {
        if (entity == null) {
            return null;
        }
        MessageVO vo = new MessageVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
} 