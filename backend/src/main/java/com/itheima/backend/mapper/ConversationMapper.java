package com.itheima.backend.mapper;

import com.itheima.backend.model.entity.Conversation;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 会话 Mapper 接口
 */
@Mapper
public interface ConversationMapper {
    
    /**
     * 插入会话
     *
     * @param conversation 会话对象
     * @return 影响行数
     */
    @Insert("INSERT INTO conversation (title, created_at, updated_at) VALUES (#{title}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Conversation conversation);
    
    /**
     * 根据ID查询会话
     *
     * @param id 会话ID
     * @return 会话对象
     */
    @Select("SELECT * FROM conversation WHERE id = #{id}")
    Conversation findById(Long id);
    
    /**
     * 查询所有会话
     *
     * @return 会话列表
     */
    @Select("SELECT * FROM conversation ORDER BY updated_at DESC")
    List<Conversation> findAll();
    
    /**
     * 更新会话
     *
     * @param conversation 会话对象
     * @return 影响行数
     */
    @Update("UPDATE conversation SET title = #{title}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(Conversation conversation);
    
    /**
     * 删除会话
     *
     * @param id 会话ID
     * @return 影响行数
     */
    @Delete("DELETE FROM conversation WHERE id = #{id}")
    int deleteById(Long id);
    
    /**
     * 搜索会话
     *
     * @param keyword 关键词
     * @return 会话列表
     */
    @Select("SELECT * FROM conversation WHERE title LIKE CONCAT('%', #{keyword}, '%') ORDER BY updated_at DESC")
    List<Conversation> search(@Param("keyword") String keyword);
} 