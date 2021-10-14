package com.gebiafu.mapper;

import com.gebiafu.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * title: UserMapper
 * author: Gebiafu
 * date: 2021/06/30 09:54
 */
@Mapper
public interface UserMapper {
    //根据用户名查信息
    @Select("select id,username,password,name from tb_user where username=#{param1}")
    User select(String username);
}
