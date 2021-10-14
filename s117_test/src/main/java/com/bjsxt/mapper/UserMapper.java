package com.bjsxt.mapper;

import com.bjsxt.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    // 新增用户
    @Insert("insert into f_user(id, username, name, password, authorities) " +
            "values(default, #{username}, #{name}, #{password}, #{authorities})")
    int insert(User user);
    // 根据用户名查询用户
    @Select("select id, username, name, password, authorities " +
            "from f_user where username = #{username}")
    User selectByUsername(String username);
}
