package com.fortwelve.wechatstore.dao;

import com.fortwelve.wechatstore.pojo.ManagerRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ManagerRoleMapper {
    @Insert("insert into manager_role (role_name) values (#{role_name})")
    @Options(useGeneratedKeys = true,keyProperty = "role_id",keyColumn = "role_id")
    public int addManagerRole(ManagerRole managerRole);

    @Delete("delete from manager_role where role_id=#{role_id}")
    public int deleteManagerRoleById(int role_id);

    @Select("select * from manager_role where role_id=#{role_id}")
    public ManagerRole getManagerRoleById(int role_id);

    @Select("select * from manager_role")
    public List<ManagerRole> getAllManagerRole();

    @Update("update manager_role set role_name=#{role_name} where role_id=#{role_id}")
    public int updateManagerRole(ManagerRole managerRole);
}
