package com.bjsxt.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemFile implements Serializable {
    private Long id;
    private String fileName;
    private String originalFileName;
    private long fileSize;
    private Long userId;
}
