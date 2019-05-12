package cn.itcast.core.controller;

import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.service.CollectService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by wang on 2019/5/11.
 */
@RestController
@RequestMapping("/collect")
public class CollectController {
    @Reference
    private CollectService collectService;
    @RequestMapping(value = "/addGoodsToCollect",method = RequestMethod.GET)
    @CrossOrigin(origins = {"http://localhost:9004"},allowCredentials = "true")
    public Result addGoodsToCollect(Long itemId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            collectService.addGoodsToCollect(itemId,username);
            return new Result(true,"成功");
        }catch (Exception e){
            return new Result(false,"失败");
        }
    }
    @RequestMapping("/showCollect")
    public List<Item> showCollect(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return collectService.showCollect(username);
    }
}
