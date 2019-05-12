package cn.itcast.core.dao.role;

import cn.itcast.core.pojo.role.Role;

import java.util.List;

/**
 * Created by wang on 2019/5/8.
 */
public interface RoleDao {
    Role findOne(Integer id);
    List<Role> findRoleList();
}
