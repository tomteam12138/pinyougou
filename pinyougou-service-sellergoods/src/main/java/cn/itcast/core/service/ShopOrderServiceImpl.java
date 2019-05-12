package cn.itcast.core.service;

import cn.itcast.core.dao.item.ItemDao;
import cn.itcast.core.dao.log.PayLogDao;
import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderItemDao;

import cn.itcast.core.pojo.order.Order;

import cn.itcast.core.pojo.order.OrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

@Service
@Transactional
public class ShopOrderServiceImpl implements ShopOrderService {
    @Autowired
    private OrderDao orderDao;
    //订单查询
    @Override
    public PageResult search(Integer page, Integer rows, Order order) {
        //分页小助手
        PageHelper.startPage(page, rows);
        //查询分页对象
        OrderQuery orderQuery = new OrderQuery();
        orderQuery.createCriteria().andSellerIdEqualTo(order.getSellerId());
        //查询分页对象
        Page<Order> p = (Page<Order>) orderDao.selectByExample(orderQuery);
        return new PageResult(p.getTotal(), p.getResult());
    }
}
