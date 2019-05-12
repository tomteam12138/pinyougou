package cn.itcast.core.controller;

import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.service.SeckillOrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seckillOrder")
public class SeckillOrderController {
    @Reference
    private SeckillOrderService seckillOrderService;
    @RequestMapping("/findAll")
    public List<SeckillOrder> findAll(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
     return    seckillOrderService.findAll(name);
    }

    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows, @RequestBody SeckillOrder seckillOrder){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        seckillOrder.setUserId(name);
        return seckillOrderService.search(page,rows,seckillOrder);
    }

}
