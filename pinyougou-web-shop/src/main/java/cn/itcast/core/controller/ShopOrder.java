package cn.itcast.core.controller;

import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.service.ShopOrderService;

import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class ShopOrder {
    @Reference
    private ShopOrderService shopOrderService;
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows, @RequestBody Order order){
        //商家ID
        order.setSellerId(SecurityContextHolder.getContext().getAuthentication().getName());
        return shopOrderService.search(page,rows,order);
    }
}
