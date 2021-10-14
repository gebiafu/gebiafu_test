package com.bjsxt.service.impl;

import com.bjsxt.exception.DaoException;
import com.bjsxt.mapper.SystemFileMapper;
import com.bjsxt.mapper.UserMapper;
import com.bjsxt.pojo.SystemFile;
import com.bjsxt.pojo.User;
import com.bjsxt.service.FileUploadService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    // 保存文件的文件夹名称。
    private final String dir = "D:\\uploadFiles";
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SystemFileMapper systemFileMapper;

    /**
     * 通过文件的名称，获取文件内容。
     * @param fileName
     * @return
     */
    @Override
    public byte[] getFile(String fileName) {
        // 创建要读取的文件对象
        File file = new File(new File(dir), fileName);
        // 创建IO流
        FileInputStream inputStream = null;
        byte[] datas = null;
        try {
            inputStream = new FileInputStream(file);
            // 创建字节数组
            datas = new byte[inputStream.available()];
            // 读取文件内容,一次性把文件的所有数据读取到字节数组中。
            inputStream.read(datas, 0, datas.length);
        }catch (Exception e){
            e.printStackTrace();
            datas = null; // 如果发生任何异常，则返回null
        }
        return datas;
    }

    /**
     * 分页查询文件，分页逻辑使用PageHelper实现
     * 查询条件是登录用户的主键
     * @param page
     * @param rows
     * @return
     */
    @Override
    public Map<String, Object> getFileByPage(int page, int rows) {
        // 获取登录用户的主键
        // 获取登录用户的用户名
        String loginUserUsername =
                SecurityContextHolder.getContext().getAuthentication().getName();
        // 查询用户
        User loginUser = userMapper.selectByUsername(loginUserUsername);
        // 用户主键
        Long userId = loginUser.getId();

        // 分页逻辑
        PageHelper.startPage(page, rows);
        // 查询文件
        List<SystemFile> list = systemFileMapper.selectByUserId(userId);
        // 查询结果是PageHelper定义的Page类型。
        PageInfo<SystemFile> pageInfo = new PageInfo<>(list);
        // 获取总计页码
        int pages = pageInfo.getPages();
        // 获取总计数量
        long total = pageInfo.getTotal();
        // 维护返回的结果
        Map<String, Object> result  = new HashMap<>();
        result.put("pages", pages); // 总计页码
        result.put("currPage", page); // 当前页码
        result.put("total", total); // 总计文件个数
        result.put("list", list); // 当前页要显示的文件集合

        return result;
    }

    /**
     * 上传文件。
     *  1. 生成随机文件名，注意不能丢失上传文件的后缀名
     *  2. 把上传的文件，保存到本地硬盘中。
     *     保存的文件统一存放在本地硬盘D:/uploadFiles文件夹中。
     *     保存的时候，要判断文件夹是否存在，不存在则创建。
     *  3. 收集上传文件的相关数据，保存到数据库
     *
     * @param file
     * @return
     */
    @Override
    @Transactional(rollbackFor = {DaoException.class})
    public boolean uploadFile(MultipartFile file) {
        String randomFileName = "";
        try {
            // 获取文件的原始名称。
            String originalFileName = file.getOriginalFilename();
            // 文件后缀名变量
            String extName = "";
            // 获取文件的后缀名
            if (originalFileName.indexOf(".") >= 0) {
                // 文件原始名称中有字符'.'
                extName = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            // 生成一个随机字符串，UUID。作为文件的随机名称
            randomFileName = UUID.randomUUID().toString() + extName;
            System.out.println("随机文件名=" + randomFileName +
                    " ; 原始文件名=" + originalFileName);

            // 保存上传的文件到本地目录。
            // 判断文件夹是否存在。如果不存在，则创建
            File dir = new File(this.dir);
            if (!dir.exists()) {
                // 文件夹不存在，创建文件夹
                dir.mkdir();
            }

            // 保存文件到本地
            // 在文件夹dir中创建文件
            File persistFile = new File(dir, randomFileName);
            // 保存文件到本地
            try {
                file.transferTo(persistFile);
            } catch (IOException e) {
                // 保存文件到硬盘失败
                e.printStackTrace();
                return false;
            }

            // 获取登录用户的用户名。
            String loginUserUsername =
                    SecurityContextHolder // Security框架提供的上下文处理工具。
                            .getContext() // 获取一个请求对应的上下文对象。上下文对象是和线程相关的。使用ThreadLocal维护。
                            .getAuthentication() // 获取一个请求对应的登录认证用户主体对象。
                            .getName(); // 获取登录认证用户的用户名

            // 根据用户名查询用户对象
            User user = userMapper.selectByUsername(loginUserUsername);
            // 获取用户的主键
            Long userId = user.getId();
            System.out.println("登录用户的主键是：" + userId);

            // 创建上传文件对应的实体类型对象
            SystemFile systemFile = new SystemFile(
                    null,
                    randomFileName, // 随机名称
                    originalFileName, // 原始名称
                    file.getSize(), // 文件大小
                    userId // 登录用户主键
            );
            // 保存到数据库
            int rows = systemFileMapper.insert(systemFile);
            // 判断新增是否成功
            if (rows != 1) {
                throw new DaoException("新增文件到数据库发生错误");
            }

            // 上传文件成功，保存文件相关数据到数据库成功。
            return true;
        }catch (DaoException e){
            this.uploadFileRollback(randomFileName);
            throw e;
        }catch (Exception e){
            this.uploadFileRollback(randomFileName);
            throw new DaoException("上传文件时，其他错误", e);
        }
    }

    /**
     * 当保存上传文件到本地硬盘后，数据库访问失败的时候，执行的方法。
     * 逻辑是，删除刚上传的文件
     */
    private void uploadFileRollback(String fileName){
        File file = new File(new File(dir), fileName);
        if(file.exists()){
            // 文件存在，删除
            file.delete();
        }
    }
}
