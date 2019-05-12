package cn.itcast.core.controller;

import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.service.OrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.Result;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Ref;

/**
 * Created by wang on 2019/4/29.
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    private OrderService orderService;

    @RequestMapping("/add")
    public Result add(@RequestBody Order order){
        try {
            //拿到名字
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            order.setUserId(name);
            orderService.add(order);
            return new Result(true,"成功");
        }catch (Exception e){
            return new Result(false,"失败");
        }
    }
}
