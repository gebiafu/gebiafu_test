package com.bjsxt.service.impl;

import com.bjsxt.exception.DaoException;
import com.bjsxt.mapper.UserMapper;
import com.bjsxt.pojo.User;
import com.bjsxt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Value("${sys.default.authorities}")
    private String defaultAuthorities;
    @Autowired // 密码加密工具
    private PasswordEncoder passwordEncoder;

    /**
     * 注册， 没有权限管理系统。 注册用户的权限，都给默认权限。
     *
     * 异常：
     *  异常的传递： 传递什么异常？是否需要再加工？
     *  异常的处理： try.catch，处理异常。
     *
     * @param user
     * @return
     */
    @Override
    @Transactional(rollbackFor = {DaoException.class})
    public int register(User user, String repeatPassword) {
        try {
            // 校验请求参数中的两次密码是否一致。
            if(repeatPassword == null || !repeatPassword.equals(user.getPassword())){
                // 两次密码不一致。或有错误参数。
                throw new DaoException("注册用户数据错误");
            }
            // 加密用户的密码
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            // 提供默认的权限
            if (user.getAuthorities() == null) {
                user.setAuthorities(defaultAuthorities);
            }
            // 保存用户到数据库
            int rows = userMapper.insert(user);
            // 判断新增数据的结果
            if (rows != 1) {
                // 新增数据到数据库发生错误。
                throw new DaoException("用户注册错误");
            }
            return rows;
        } catch (DaoException e){
            // 代码中通过判断逻辑抛出的异常。直接传递给控制器
            throw e;
        } catch (Exception e){
            // MyBatis抛出异常。处理后封装成自定义异常传递给控制器
            throw new DaoException("用户注册错误", e);
        }
    }

    @Override
    public User login(String username) {
        try {
            return userMapper.selectByUsername(username);
        }catch (Exception e){
            throw new DaoException("用户登录错误", e);
        }
    }
}
