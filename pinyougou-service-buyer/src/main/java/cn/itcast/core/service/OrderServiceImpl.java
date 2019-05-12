package cn.itcast.core.service;

import cn.itcast.core.dao.log.PayLogDao;
import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.pojo.log.PayLog;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.order.OrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;
import vo.Cart;
import vo.OrderVo;

import java.math.BigDecimal;
import java.util.*;

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
//运营商后台分页查询订单统计(未实现)

    public PageResult search(Integer pageNum, Integer pageSize, Order order) {
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orderListGroup = orderDao.selectGroupBySellerId();
        List<OrderVo> list=new ArrayList<>();
        for (Order o : orderListGroup) {
            OrderQuery orderQuery=new OrderQuery();
            System.out.println(o.getSellerId());
            orderQuery.createCriteria().andSellerIdEqualTo(o.getSellerId());
            List<Order> orderList = orderDao.selectByExample(orderQuery);
            OrderVo vo=new OrderVo();
            vo.setOrderList(orderList);
            System.out.println();
            list.add(vo);
        }
       Page<OrderVo> page = (Page<OrderVo>) list;
        System.out.println();
        return new PageResult(page.getTotal(),page.getResult());
    }
    @Override
    public List<OrderVo> searchOrder( ) {
        List<Order> orderListGroup = orderDao.selectGroupBySellerId();
       List<OrderVo> orderVoList=new ArrayList<>();
        for (Order o : orderListGroup) {
            OrderQuery orderQuery=new OrderQuery();
            System.out.println(o.getSellerId());
            orderQuery.createCriteria().andSellerIdEqualTo(o.getSellerId());
            List<Order> orderList = orderDao.selectByExample(orderQuery);
           OrderVo orderVo=new OrderVo();
           orderVo.setOrderList(orderList);
           orderVo.setSellerId(o.getSellerId());
           orderVoList.add(orderVo);
        }
        return orderVoList;
    }
    //查询订单总数
    @Override
    public Integer findOrderTotal() {
       return orderDao.countByExample(null);
    }
}
