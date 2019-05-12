package cn.itcast.core.service;

import cn.itcast.core.pojo.order.Order;
import entity.PageResult;
import vo.OrderVo;

import java.util.List;
import java.util.Map;

/**
 * Created by wang on 2019/4/29.
 */
public interface OrderService {
    void add(Order order);

    PageResult search(Integer pageNum, Integer pageSize, Order order);

    List<OrderVo> searchOrder();

    Integer findOrderTotal();
}
