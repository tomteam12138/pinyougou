package cn.itcast.core.controller;

import cn.itcast.core.service.OrderItemService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orderList")
public class OrderListController {
    @Reference
    OrderItemService orderItemService;

    @RequestMapping("/findPic")
    public Map<String, List> findPic() {
        return orderItemService.findPic();
    }
    @RequestMapping("/findPic2")
    public List<Map> findPic2(){
        return orderItemService.findPic2();
    }
}

