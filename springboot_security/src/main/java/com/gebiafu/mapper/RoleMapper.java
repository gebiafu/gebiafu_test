package com.gebiafu.mapper;

import com.gebiafu.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * title: RoleMapper
 * author: Gebiafu
 * date: 2021/07/01 14:34
 */
@Mapper
public interface RoleMapper {
    //根据用户主键查询角色集合
    @Select("select r.id,r.name,r.remark "+
            "from tb_roles r left join tb_user_role ur on r.id=ur.role_id "+
            "where ur.user_id=#{userId}")
    List<Role> selectByUserId(Integer userId);
}
