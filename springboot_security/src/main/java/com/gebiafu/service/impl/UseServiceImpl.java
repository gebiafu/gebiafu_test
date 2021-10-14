package com.gebiafu.service.impl;

import com.gebiafu.mapper.ResourceMapper;
import com.gebiafu.mapper.RoleMapper;
import com.gebiafu.mapper.UserMapper;
import com.gebiafu.pojo.Resource;
import com.gebiafu.pojo.Role;
import com.gebiafu.pojo.User;
import com.gebiafu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.sql.ResultSet;
import java.util.List;

/**
 * title: UseServiceImpl
 * author: Gebiafu
 * date: 2021/06/30 09:56
 */
@Service
public class UseServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private ResourceMapper resourceMapper;


    @Override
    public User findOne(String username) {
        return userMapper.select(username);
    }

    @Override
    public List<Role> getRolesByUser(User user) {
        return roleMapper.selectByUserId(user.getId());
    }

    @Override
    public List<Resource> getResourcesByUser(User user) {
        return resourceMapper.selectResourcesByUserId(user.getId());
    }
}
