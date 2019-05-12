package cn.itcast.core.service;

import cn.itcast.core.pojo.user.User; /**
 * Created by wang on 2019/4/26.
 */
public interface UserService {
    void sendCode(String phone);

    void add(String smscode, User user);

    User findUserAndRole(String username);
}
