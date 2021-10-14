package com.bjsxt.controller;

import com.bjsxt.exception.DaoException;
import com.bjsxt.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 下载
     * 通过响应输出流，向客户端输出数据，需要定制响应头和附件信息
     */
    @RequestMapping("/download")
    @PreAuthorize("hasAuthority('/download')")
    public void download(String fileName, String originalFileName, HttpServletResponse response){
        // 查询文件内容
        byte[] datas = fileUploadService.getFile(fileName);
        // 设置响应头
        response.setContentType("application/octet-stream");
        // 设置附件
        response.setHeader("content-disposition", "attachment;filename="+originalFileName);
        // 通过响应输出流，把文件输出到客户端
        try {
            response.getOutputStream().write(datas, 0, datas.length);
            response.getOutputStream().flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 预览图片
     * @param fileName 要预览的图片名称
     */
    @RequestMapping("/image")
    @PreAuthorize("hasAuthority('/image')")
    public void show(String fileName, HttpServletResponse response){
        // 通过服务，获取图片的所有字节数据
        byte[] datas = fileUploadService.getFile(fileName);
        if(datas != null) {

            try {
                // "image/jpeg"
                response.setContentType("application/octet-stream");
                // 使用响应输出流，向客户端输出字节数据。
                response.getOutputStream().write(datas, 0, datas.length);
                response.getOutputStream().flush();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 跳转到图片预览页面
     * @return
     */
    @RequestMapping("/showImage")
    @PreAuthorize("hasAuthority('/showImage')")
    public String showImage(String fileName, Model model){
        model.addAttribute("fileName", fileName);
        return "showImage";
    }

    /**
     * 登录成功后的跳转方法。
     * 有/toMain权限的登录用户可访问
     * 跳转页面前，查询当前登录用户上传的文件。
     * 分页查询。
     * 使用请求作用域传递查询结果到页面。
     * @param page 第几页
     * @param rows 多少行
     * @return
     */
    @RequestMapping("/toMain")
    @PreAuthorize("hasAuthority('/toMain')")
    public String toMain(@RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "2") int rows,
                         Model model){
        // 查询文件
        Map<String, Object> result = fileUploadService.getFileByPage(page, rows);
        // 通过请求作用域，传递数据到页面
        model.addAllAttributes(result);
        // 返回视图页面
        return "main";
    }


    /**
     * 上传文件
     *  1. 解决文件随机命名问题。
     *  2. 解决文件存储到本地硬盘问题。
     *  3. 收集上传文件的相关数据，并保存到数据库。
     *     文件的名字，原始名字，上传用户等。 缺少登录用户的用户名
     * @return
     */
    @RequestMapping("/upload")
    @PreAuthorize("hasAuthority('/upload')")
    public String upload(MultipartFile file){
        try {
            if (fileUploadService.uploadFile(file)) {
                // 上传文件成功
                return "redirect:/toMain";
            } else {
                // 上传文件失败
                return "redirect:/fileUploadFailure";
            }
        }catch (DaoException e){
            // 抛出异常了。上传文件失败
            return "redirect:/fileUploadFailure";
        }

    }
}
