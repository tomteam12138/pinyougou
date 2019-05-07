package cn.itcast.core.controller;

import cn.itcast.core.pojo.user.User;
import cn.itcast.core.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.PhoneFormatCheckUtils;

/**
 * Created by wang on 2019/4/26.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;
    @RequestMapping("/sendCode")
    public Result sendConde(String phone){
        try {
            if (PhoneFormatCheckUtils.isPhoneLegal(phone)){
                userService.sendCode(phone);
                return new Result(true,"短信发送成功");
            }
            return new Result(false,"手机号格式不正确");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"短信发送失败");
        }
    }

    @RequestMapping("/add")
    public Result add(String smscode,@RequestBody User user){
        try {
            userService.add(smscode,user);
            return new Result(true,"注册成功");

        }catch (RuntimeException re){
            return new Result(false, re.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return new Result(false,"注册失败");
        }
    }
}
