package cn.itcast.core.controller;

import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.service.SeckillService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seckill")
public class SecKillController {
    @Reference
    private SeckillService seckillService;
    @RequestMapping("/findSeckillOrder")
    public SeckillOrder findSeckillOrder(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
       return seckillService.findSeckillOrder(name);
    }

    @RequestMapping("/ delSeckill")
    public Result delSeckill(Long id){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            seckillService.delSeckill(name,id);
            return new Result(true,"取消订单成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"取消失败");
        }

    }
}
