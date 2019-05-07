package cn.itcast.core.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wang on 2019/4/10.
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @RequestMapping("/showName")
    public Map<String,String> login(){
        Map<String,String> map = new HashMap<>();
        User principal =(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        map.put("username",username);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        String logintime = simpleDateFormat.format(new Date());
        map.put("logintime",logintime);
        return map;
    }
}
