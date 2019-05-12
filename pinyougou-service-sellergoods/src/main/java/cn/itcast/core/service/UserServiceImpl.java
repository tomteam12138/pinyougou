package cn.itcast.core.service;

import cn.itcast.core.dao.role.RoleDao;
import cn.itcast.core.dao.user.UserDao;
import cn.itcast.core.dao.user_role.UserAndRoleDao;
import cn.itcast.core.pojo.role.Role;
import cn.itcast.core.pojo.user.User;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wang on 2019/5/8.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService2 {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserAndRoleDao userAndRoleDao;
    @Autowired
    private RoleDao roleDao;

    @Override
    public PageResult findAll(Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        Page<User> userList = (Page<User>) userDao.findAll();
        List<User> result = userList.getResult();
        for (User user : result) {
            System.out.println(user.getRole());
        }
        return new PageResult(userList.getTotal(),userList.getResult());
    }

    @Override
    public List<Role> findRoleList() {
        return roleDao.findRoleList();
    }

    @Override
    public void save(Integer userId,Integer roleId) {
        userAndRoleDao.del(userId);
        userAndRoleDao.insert(userId,roleId);
    }

    @Override
    public User findOne(String username) {
        return userDao.findOne(username);
    }
}
