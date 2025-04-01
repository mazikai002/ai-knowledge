package com.itheima.backend.dao;

import com.itheima.backend.model.entity.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MessageMapper {
    @Select("SELECT * FROM messages WHERE conversation_id = #{conversationId}")
    List<Message> findByConversationId(Long conversationId);

    @Insert("INSERT INTO messages (content, type, model, created_at, conversation_id) " +
            "VALUES (#{content}, #{type}, #{model}, #{createdAt}, #{conversationId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Message message);
} 