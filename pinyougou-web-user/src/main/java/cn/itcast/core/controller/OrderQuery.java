package cn.itcast.core.controller;
import cn.itcast.core.service.OrderQueryService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.UserVo;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/query")
public class OrderQuery {
    @Reference
    private OrderQueryService orderQueryService;
    @RequestMapping("/orderquert")
    public List<UserVo> orderquert() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return orderQueryService.orderquert(name);
    }
}
