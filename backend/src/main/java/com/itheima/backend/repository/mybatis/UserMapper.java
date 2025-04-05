package com.itheima.backend.repository.mybatis;

import com.itheima.backend.model.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 用户Mapper接口
 */
// @Mapper 注解已通过@MapperScan在应用主类中全局配置，这里可以省略
public interface UserMapper {
    
    /**
     * 根据用户名查询用户
     */
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(String username);
    
    /**
     * 根据用户名和密码查询用户
     */
    @Select("SELECT * FROM user WHERE username = #{username} AND password = #{password}")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    
    /**
     * 新增用户
     */
    @Insert("INSERT INTO user (username, password, nickname, email, avatar, status, create_time, update_time) " +
            "VALUES (#{username}, #{password}, #{nickname}, #{email}, #{avatar}, #{status}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);
    
    /**
     * 更新用户
     */
    @Update("UPDATE user SET " +
            "nickname = #{nickname}, " +
            "email = #{email}, " +
            "avatar = #{avatar}, " +
            "status = #{status}, " +
            "update_time = #{updateTime} " +
            "WHERE id = #{id}")
    int update(User user);
    
    /**
     * 根据ID查询用户
     */
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(Long id);
} 