package com.gebiafu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * title: Resource
 * author: Gebiafu
 * date: 2021/07/01 14:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resource implements Serializable {
    private Integer id;
    private String name;
    private String url;
    private String remark;
    private Integer parentId;
}
