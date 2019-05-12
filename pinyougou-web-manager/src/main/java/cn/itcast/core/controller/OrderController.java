package cn.itcast.core.controller;

import cn.itcast.core.service.OrderService;
import com.alibaba.dubbo.config.annotation.Reference;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.OrderVo;

import java.util.List;


/**
 * Created by wang on 2019/4/6.
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    private OrderService orderService;
    //关键字搜索功能带分页
//    @RequestMapping("/search")
//    public PageResult search(Integer pageNum,Integer pageSize,@RequestBody(required = false)Order order) {
//        return  orderService.search(pageNum, pageSize, order);
//    }
    @RequestMapping("/searchOrder")
    public List<OrderVo> searchOrder() {
        return  orderService.searchOrder();
    }
    @RequestMapping("/findOrderTotal")
    public  Integer findOrderTotal(){
        return orderService.findOrderTotal();
    }
}
