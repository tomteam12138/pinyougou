package cn.itcast.core.service;

import cn.itcast.core.pojo.role.Role;
import cn.itcast.core.pojo.user.User;
import entity.PageResult;

import java.util.List;

/**
 * Created by wang on 2019/4/26.
 */
public interface UserService2 {

    PageResult findAll(Integer page, Integer rows);

    List<Role> findRoleList();

    void save(Integer userId,Integer roleId);

    User findOne(String username);
}
