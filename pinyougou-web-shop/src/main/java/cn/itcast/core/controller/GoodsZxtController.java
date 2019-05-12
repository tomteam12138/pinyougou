package cn.itcast.core.controller;


import cn.itcast.core.service.GoodsZxtService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/goodsZxt")
public class GoodsZxtController {
    @Reference
    private GoodsZxtService goodsZxtService;
    @RequestMapping("/find")
    public Map<String,Object> find(){
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        return   goodsZxtService.find(sellerId);
    }
}
