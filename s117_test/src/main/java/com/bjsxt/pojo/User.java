package com.bjsxt.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private Long id; // 主键
    private String username; // 登录名
    private String name; // 姓名
    private String password; // 密码
    private String authorities; // 权限
}
