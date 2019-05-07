package cn.itcast.core.service;

import cn.itcast.core.dao.log.PayLogDao;
import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.pojo.log.PayLog;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;
import vo.Cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wang on 2019/4/29.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private PayLogDao payLogDao;
    @Override
    public void add(Order order) {
        order.setUpdateTime(new Date());
        order.setCreateTime(new Date());
        order.setStatus("0");
        List<String> orderIdList = new ArrayList<>();
        double totalPrice = 0;
        double totalPrice2 = 0;
        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("cart").get(order.getUserId());
        for (Cart cart : cartList) {
            order.setOrderId(idWorker.nextId());
            List<OrderItem> orderItemList = cart.getOrderItemList();
            for (OrderItem orderItem : orderItemList) {
                orderItem.setId(idWorker.nextId());
                orderItem.setSellerId(cart.getSellerId());
                orderItem.setOrderId(order.getOrderId());
                orderItemDao.insertSelective(orderItem);
                totalPrice += orderItem.getTotalFee().doubleValue();
            }
            order.setPayment(new BigDecimal(totalPrice));
            totalPrice = 0;
            orderIdList.add(String.valueOf(order.getOrderId()));
            totalPrice2 += order.getPayment().doubleValue();
            orderDao.insertSelective(order);
        }
        PayLog payLog = new PayLog();
        payLog.setOutTradeNo(String.valueOf(idWorker.nextId()));
        payLog.setCreateTime(new Date());
        payLog.setPayType(order.getPaymentType());
        payLog.setTotalFee(new BigDecimal(totalPrice2*100).longValue());
        payLog.setUserId(order.getUserId());
        payLog.setOrderList(orderIdList.toString().replace("[","").replace("[",""));
        payLogDao.insertSelective(payLog);
    }
}
