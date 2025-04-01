package com.itheima.backend.model.converter;

import com.itheima.backend.model.dto.ConversationDTO;
import com.itheima.backend.model.entity.Conversation;
import com.itheima.backend.model.vo.ConversationVO;
import org.springframework.beans.BeanUtils;

/**
 * 会话对象转换器
 */
public class ConversationConverter {
    
    /**
     * DTO 转 Entity
     */
    public static Conversation toEntity(ConversationDTO dto) {
        if (dto == null) {
            return null;
        }
        Conversation entity = new Conversation();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
    
    /**
     * Entity 转 VO
     */
    public static ConversationVO toVO(Conversation entity) {
        if (entity == null) {
            return null;
        }
        ConversationVO vo = new ConversationVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
} 