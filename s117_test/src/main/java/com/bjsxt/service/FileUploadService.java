package com.bjsxt.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileUploadService {
    /**
     * 上传文件
     * @return 成功-true； 失败-false
     */
    boolean uploadFile(MultipartFile file);
    /**
     * 分页查询文件
     */
    Map<String, Object> getFileByPage(int page, int rows);
    /**
     * 通过文件的名称，获取文件的全部数据，使用字节数组返回。
     */
    byte[] getFile(String fileName);
}
