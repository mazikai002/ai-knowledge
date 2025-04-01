package com.itheima.backend.dao;

import com.itheima.backend.model.entity.Conversation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ConversationMapper {
    @Select("SELECT * FROM conversations")
    List<Conversation> findAll();

    @Insert("INSERT INTO conversations (title, last_message, created_at, updated_at) " +
            "VALUES (#{title}, #{lastMessage}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Conversation conversation);

    @Delete("DELETE FROM conversations WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT * FROM conversations WHERE title LIKE CONCAT('%', #{keyword}, '%') " +
            "OR last_message LIKE CONCAT('%', #{keyword}, '%')")
    List<Conversation> search(String keyword);

    @Update("UPDATE conversations SET last_message = #{lastMessage}, updated_at = #{updatedAt} " +
            "WHERE id = #{id}")
    int updateLastMessage(Conversation conversation);
} 