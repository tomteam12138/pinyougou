package cn.itcast.core.controller;

import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.service.SeckillGoodsService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by wang on 2019/5/9.
 */
@RestController
@RequestMapping("/seckillGoods")
public class SeckillGoodsController {
    @Reference(timeout=10000)
    private SeckillGoodsService seckillGoodsService;
    @RequestMapping("/findList")
    public List<SeckillGoods> findList(){
        return seckillGoodsService.findList();
    }

    @RequestMapping("/findOneFromRedis")
    public SeckillGoods findOneFromRedis(Long id){
        return seckillGoodsService.findOneFromRedis(id);
    }


}
