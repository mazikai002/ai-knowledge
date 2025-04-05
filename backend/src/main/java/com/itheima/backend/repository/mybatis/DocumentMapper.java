package com.itheima.backend.repository.mybatis;

import com.itheima.backend.model.entity.Document;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 文档 Mapper 接口
 */
// @Mapper 注解已通过@MapperScan在应用主类中全局配置，这里可以省略
public interface DocumentMapper {
    
    /**
     * 插入文档
     *
     * @param document 文档对象
     * @return 影响行数
     */
    @Insert("INSERT INTO document (title, content, created_at, updated_at) VALUES (#{title}, #{content}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Document document);
    
    /**
     * 根据ID查询文档
     *
     * @param id 文档ID
     * @return 文档对象
     */
    @Select("SELECT * FROM document WHERE id = #{id}")
    Document findById(Long id);
    
    /**
     * 查询所有文档
     *
     * @return 文档列表
     */
    @Select("SELECT * FROM document ORDER BY updated_at DESC")
    List<Document> findAll();
    
    /**
     * 更新文档
     *
     * @param document 文档对象
     * @return 影响行数
     */
    @Update("UPDATE document SET title = #{title}, content = #{content}, updated_at = #{updatedAt} WHERE id = #{id}")
    int update(Document document);
    
    /**
     * 删除文档
     *
     * @param id 文档ID
     * @return 影响行数
     */
    @Delete("DELETE FROM document WHERE id = #{id}")
    int deleteById(Long id);
    
    /**
     * 根据标题模糊查询文档
     *
     * @param title 标题关键词
     * @return 文档列表
     */
    @Select("SELECT * FROM document WHERE title LIKE CONCAT('%', #{title}, '%') ORDER BY updated_at DESC")
    List<Document> findByTitleLike(String title);
} 