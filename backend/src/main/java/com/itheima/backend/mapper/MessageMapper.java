package com.itheima.backend.mapper;

import com.itheima.backend.entity.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 消息 Mapper 接口
 */
@Mapper
public interface MessageMapper {
    
    /**
     * 插入消息
     *
     * @param message 消息对象
     * @return 影响行数
     */
    @Insert("INSERT INTO message (conversation_id, role, content, created_at) VALUES (#{conversationId}, #{role}, #{content}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Message message);
    
    /**
     * 根据会话ID查询消息列表
     *
     * @param conversationId 会话ID
     * @return 消息列表
     */
    @Select("SELECT * FROM message WHERE conversation_id = #{conversationId} ORDER BY created_at ASC")
    List<Message> findByConversationId(Long conversationId);
    
    /**
     * 根据ID查询消息
     *
     * @param id 消息ID
     * @return 消息对象
     */
    @Select("SELECT * FROM message WHERE id = #{id}")
    Message findById(Long id);
    
    /**
     * 更新消息
     *
     * @param message 消息对象
     * @return 影响行数
     */
    @Update("UPDATE message SET content = #{content} WHERE id = #{id}")
    int update(Message message);
    
    /**
     * 删除消息
     *
     * @param id 消息ID
     * @return 影响行数
     */
    @Delete("DELETE FROM message WHERE id = #{id}")
    int deleteById(Long id);
    
    /**
     * 删除会话下的所有消息
     *
     * @param conversationId 会话ID
     * @return 影响行数
     */
    @Delete("DELETE FROM message WHERE conversation_id = #{conversationId}")
    int deleteByConversationId(Long conversationId);
} 