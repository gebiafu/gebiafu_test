package com.gebiafu.service;

import com.gebiafu.pojo.Resource;
import com.gebiafu.pojo.Role;
import com.gebiafu.pojo.User;

import java.util.List;

/**
 * title: UserService
 * author: Gebiafu
 * date: 2021/06/30 09:55
 */
public interface UserService {
    User findOne(String username);

    List<Role> getRolesByUser(User user);

    List<Resource> getResourcesByUser(User user);
}
