package cn.itcast.core.controller;

import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.service.SeckillService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seckill")
public class SeckillOrderController {
    @Reference
    private SeckillService seckillService;

    @RequestMapping("/seckillorder")
    public PageResult seckillorder(Integer page, Integer rows, @RequestBody(required = false) SeckillOrder seckillOrder) {
        //直接查
        return seckillService.seckillorder(page, rows);
    }
}
