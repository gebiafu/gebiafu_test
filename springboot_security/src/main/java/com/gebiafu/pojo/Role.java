package com.gebiafu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * title: Role
 * author: Gebiafu
 * date: 2021/07/01 14:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {
    private Integer id;
    private String name;
    private String remark;
}
