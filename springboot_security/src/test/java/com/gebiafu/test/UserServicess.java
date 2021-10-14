package com.gebiafu.test;

import com.gebiafu.mapper.UserMapper;
import com.gebiafu.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * title: UserServicess
 * author: Gebiafu
 * date: 2021/07/01 21:25
 */
@SpringBootTest
public class UserServicess {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void testUser(){
        User zs = userMapper.select("zs");
        System.out.println(zs
        );
    }
}
