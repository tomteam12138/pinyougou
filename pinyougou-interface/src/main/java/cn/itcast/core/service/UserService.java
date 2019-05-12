package cn.itcast.core.service;

import cn.itcast.core.pojo.user.User;
import entity.PageResult;

import java.util.List;

/**
 * Created by wang on 2019/4/26.
 */
public interface UserService {
    void sendCode(String phone);

    void add(String smscode, User user);

    PageResult search(Integer pageNum, Integer pageSize, User user);

    Integer findTotal();

}
