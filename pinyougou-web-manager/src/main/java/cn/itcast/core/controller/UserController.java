package cn.itcast.core.controller;


import cn.itcast.core.pojo.role.Role;
import cn.itcast.core.service.UserService2;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Created by wang on 2019/5/8.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService2 userService2;
    @RequestMapping("/findAll")
    public PageResult findAll(Integer pageNum, Integer pageSize){
        PageResult all = userService2.findAll(pageNum, pageSize);
        return all;
    }

    @RequestMapping("/findRoleList")
    public List<Role> findRoleList(){
        return userService2.findRoleList();
    }

    @RequestMapping("/save")
    public Result save(Integer userId,Integer roleId){
        try {
            userService2.save(userId,roleId);
            return new Result(true,"修改角色成功");
        }catch (Exception e){
            return new Result(true,"修改角色失败");

        }
    }
}
