package com.gebiafu.mapper;

import com.gebiafu.pojo.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * title: ResourceMapper
 * author: Gebiafu
 * date: 2021/07/01 14:28
 */
@Mapper
public interface ResourceMapper {
    //根据用户主键.查询资源集合
    @Select("select r.id,r.name,r.url,r.remark,r.parent_id as parentId "+
            "from tb_resources r left join tb_role_resource rr on r.id=rr.resource_id "+
            "left join tb_user_role ur on rr.role_id=ur.role_id "+
            "where ur.user_id = #{userId}")
    List<Resource> selectResourcesByUserId(Integer userId);
}
