package cn.itcast.core.service;

import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.order.OrderItemQuery;
import cn.itcast.core.pojo.order.OrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import vo.UserVo;

import java.util.*;

@Service
@Transactional
public class OrderQueryServiceImpl implements OrderQueryService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Override
    public List<UserVo> orderquert(String name) {
        //创建集合用来封装order的id 跟orderItemList
        //分页助手
//        PageHelper.startPage();
        //查询user   id
        OrderQuery orderQuery = new OrderQuery();
        //比较user id与order id中相同的id
        orderQuery.createCriteria().andUserIdEqualTo(name);
        //通过user id查询到order集合
        List<Order> orderList = orderDao.selectByExample(orderQuery);
        List<UserVo> list = new ArrayList<>();
        //遍历订单 orderList拿到里面内容
        for (Order order : orderList) {
            UserVo vo = new UserVo();
            Long orderId = order.getOrderId();
            vo.setOrderId(orderId);
            vo.setStatus(order.getStatus());
            Date time = order.getCreateTime();
            vo.setTime(String.valueOf(time));
            vo.setSellerId(order.getSellerId());
            OrderItemQuery orderItemQuery = new OrderItemQuery();
            orderItemQuery.createCriteria().andOrderIdEqualTo(orderId);
            List<OrderItem> orderItemList = orderItemDao.selectByExample(orderItemQuery);
            vo.setOrderItemList(orderItemList);
            list.add(vo);
        }
        return list;
    }
}
