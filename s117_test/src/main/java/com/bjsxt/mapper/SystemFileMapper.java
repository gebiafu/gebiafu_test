package com.bjsxt.mapper;

import com.bjsxt.pojo.SystemFile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SystemFileMapper {
    @Insert("insert into f_file (id, file_name, original_file_name, file_size, user_id) " +
            "values(#{id},#{fileName},#{originalFileName},#{fileSize},#{userId})")
    int insert(SystemFile file);
    /**
     * 基于PageHelper实现分页
     * 分页查询文件
     * 查询条件是登录用户的主键。和分页逻辑
     */
    @Select("select id, file_name as fileName, original_file_name as originalFileName, " +
            "file_size as fileSize, user_id as userId " +
            "from f_file where user_id = #{userId}")
    List<SystemFile> selectByUserId(Long userId);

}
