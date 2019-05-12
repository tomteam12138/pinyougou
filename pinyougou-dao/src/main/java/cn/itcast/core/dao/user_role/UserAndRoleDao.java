package cn.itcast.core.dao.user_role;

import cn.itcast.core.pojo.userandrole.UserAndRole;

/**
 * Created by wang on 2019/5/9.
 */
public interface UserAndRoleDao {
    UserAndRole findOne(Integer userId);
    void del(Integer userId);
    void insert(Integer userId,Integer roleId);
}
