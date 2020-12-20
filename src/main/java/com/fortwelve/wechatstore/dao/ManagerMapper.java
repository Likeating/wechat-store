package com.fortwelve.wechatstore.dao;

import com.fortwelve.wechatstore.pojo.Manager;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface ManagerMapper {

    @Insert("insert into manager (manager_name,manager_password,realname,email,tel,sex,role_id) values (#{manager_name},#{manager_password},#{realname},#{email},#{tel},#{sex},#{role_id})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    public int addManager(Manager manager);

    @Delete("delete from manager where id=#{id}")
    public int deleteManagerById(int id);

    @Select("select * from manager where id=#{id}")
    public Manager getManagerById(int id);

    @Select("select * from manager")
    public List<Manager> getAllManager();

    @Update("update manager set manager_name=#{manager_name},realname=#{realname},manager_password=#{manager_password},email=#{email},sex=#{sex},tel=#{tel},role_id=#{role_id} where id=#{id}")
    public int updateManager(Manager manager);

    @Select("select * from manager where manager_name=#{manager_name}")
    public Manager getManagerByManagerName(String manager_name);
}
