package com.itheima.backend.dao;


import com.itheima.backend.model.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDAO {

    /**
     * 新增用户
     *
     * @param user 用户对象
     * @return 新增成功记录条数
     */
    @Insert("INSERT INTO user (username, password) VALUES (#{username}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")  // 返回自增的 ID
    int add(User user);

    /**
     * 修改用户信息
     *
     * @param user 用户对象
     * @return 修改成功记录条数
     */
    @Update("UPDATE user SET username = #{username}, password = #{password} WHERE id = #{id}")
    int update(User user);

    /**
     * 根据id获取用户
     *
     * @param id 用户id
     * @return 用户对象
     */
    @Select("SELECT id, username, password FROM user WHERE id = #{id}")
    User getById(Integer id);

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    @Select("SELECT id, username, password FROM user WHERE username = #{username}")
    User getByUsername(String username);
}