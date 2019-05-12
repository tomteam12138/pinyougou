package cn.itcast.core.pojo.userandrole;

import java.io.Serializable;

/**
 * Created by wang on 2019/5/9.
 */
public class UserAndRole implements Serializable {
    private Integer userId;
    private Integer roleId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
