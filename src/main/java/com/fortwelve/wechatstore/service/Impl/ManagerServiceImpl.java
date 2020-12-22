package com.fortwelve.wechatstore.service.Impl;

import com.fortwelve.wechatstore.dao.ManagerMapper;
import com.fortwelve.wechatstore.dao.ManagerRoleMapper;
import com.fortwelve.wechatstore.pojo.Manager;
import com.fortwelve.wechatstore.pojo.ManagerRole;
import com.fortwelve.wechatstore.service.ManagerService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    ManagerMapper managerMapper;
    @Autowired
    ManagerRoleMapper managerRoleMapper;

    @Override
    public int addManagerRole(ManagerRole managerRole) {
        return managerRoleMapper.addManagerRole(managerRole);
    }

    @Override
    public int deleteManagerRoleById(int role_id) {
        return managerRoleMapper.deleteManagerRoleById(role_id);
    }

    @Override
    public ManagerRole getManagerRoleById(int role_id) {
        return managerRoleMapper.getManagerRoleById(role_id);
    }

    @Override
    public List<ManagerRole> getAllManagerRole() {
        return managerRoleMapper.getAllManagerRole();
    }

    @Override
    public int updateManagerRole(ManagerRole managerRole) {
        return managerRoleMapper.updateManagerRole(managerRole);
    }

    @Override
    public int addManager(Manager manager) {
        return managerMapper.addManager(manager);
    }

    @Override
    public int deleteManagerById(int id) {
        return managerMapper.deleteManagerById(id);
    }

    @Override
    public Manager getManagerById(int id) {
        return managerMapper.getManagerById(id);
    }

    @Override
    public List<Manager> getAllManager() {
        return managerMapper.getAllManager();
    }

    @Override
    public int updateManager(Manager manager) {
        return managerMapper.updateManager(manager);
    }

    @Override
    public Manager getManagerByManagerName(String manager_name) {
        return managerMapper.getManagerByManagerName(manager_name);
    }

    @Override
    public List<Manager> getManagerPage(@Param("offset") int offset,@Param("rows") int rows){
        return managerMapper.getManagerPage(offset,rows);
    }
}
