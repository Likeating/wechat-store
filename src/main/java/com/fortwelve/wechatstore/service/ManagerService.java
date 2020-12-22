package com.fortwelve.wechatstore.service;


import com.fortwelve.wechatstore.dto.ManagerDTO;
import com.fortwelve.wechatstore.pojo.Manager;
import com.fortwelve.wechatstore.pojo.ManagerRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ManagerService {
    int addManagerRole(ManagerRole managerRole);
    int deleteManagerRoleById(int role_id);
    ManagerRole getManagerRoleById(int role_id);
    List<ManagerRole> getAllManagerRole();
    int updateManagerRole(ManagerRole managerRole);

    int addManager(Manager manager);
    int deleteManagerById(int id);
    Manager getManagerById(int id);
    List<Manager> getAllManager();
    int updateManager(Manager manager);

    Manager getManagerByManagerName(String manager_name);
    List<Manager> getManagerPage(@Param("offset") int offset,@Param("rows") int rows);
}
