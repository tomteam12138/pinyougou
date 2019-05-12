package cn.itcast.core.service;

import cn.itcast.core.pojo.order.Order;
import com.alibaba.dubbo.config.annotation.Service;
import entity.PageResult;
import org.springframework.transaction.annotation.Transactional;

//订单管理
@Service
@Transactional
public interface ShopOrderService {

    PageResult search(Integer page, Integer rows, Order order);

}
