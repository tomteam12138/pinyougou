package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillOrderDao;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.pojo.seckill.SeckillOrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SeckillOrderServiceImpl implements SeckillOrderService {
    @Autowired
    private SeckillOrderDao seckillOrderDao;
    @Override
    public List<SeckillOrder> findAll(String name) {
        SeckillOrderQuery seckillOrderQuery = new SeckillOrderQuery();
        seckillOrderQuery.createCriteria().andSellerIdEqualTo(name);
        List<SeckillOrder> orders = seckillOrderDao.selectByExample(seckillOrderQuery);
        return orders;
    }

    @Override
    public PageResult search( Integer page, Integer rows, SeckillOrder seckillOrder) {
        PageHelper.startPage(page,rows);
        SeckillOrderQuery seckillOrderQuery = new SeckillOrderQuery();
        seckillOrderQuery.createCriteria().andSellerIdEqualTo(seckillOrder.getUserId());
        Page<SeckillOrder> seckillOrders = (Page<SeckillOrder>) seckillOrderDao.selectByExample(seckillOrderQuery);
        return new PageResult(seckillOrders.getTotal(),seckillOrders.getResult());
    }
}
