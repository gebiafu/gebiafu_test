package com.bjsxt.test;

import com.bjsxt.mapper.UserMapper;
import com.bjsxt.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// 测试用户数据访问接口
@SpringBootTest
public class TestUserMapper {
    // 注入要测试的对象
    @Autowired
    private UserMapper userMapper;

    // 测试新增用户
    @Test
    public void testInsert(){
        User user = new User(null, "guest", "张三",
                "$2a$10$l3Hb3RtZLxbWE6q3KM2NjusPn9mqCaHf8hmSRtaANlSCxsnMTc9B6",
                "/toMain;/listFiles;/toUpload;/upload;/download;/logout;/login");
        System.out.println(userMapper.insert(user));
    }

    @Test
    public void testSelect(){
        System.out.println(
                userMapper.selectByUsername("admin")
        );

        System.out.println("===================================");

        System.out.println(
                userMapper.selectByUsername("guest")
        );
    }

}
