package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.dao.seckill.SeckillOrderDao;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.pojo.seckill.SeckillOrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SeckillServiceImpl implements SeckillService {
       @Autowired
       private RedisTemplate redisTemplate;
       @Autowired
       private SeckillOrderDao seckillOrderDao;
       @Autowired
       private SeckillGoodsDao seckillGoodsDao;
    @Override
    public SeckillOrder findSeckillOrder(String name) {
           //测试秒杀订单假数据(可删除)
//        SeckillOrder order = seckillOrderDao.selectByPrimaryKey(919497943340302336L);
//        redisTemplate.boundHashOps("seckillOrder").put(name,order);
//        SeckillGoods seckillGoods = seckillGoodsDao.selectByPrimaryKey(2L);
//        redisTemplate.boundHashOps("seckillGoods").put(2L,seckillGoods);


        //在redis中查询秒杀订单
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.boundHashOps("seckillOrder").get(name);
        return seckillOrder;
    }
     //删除秒杀订单
    @Override
    public void delSeckill(String name, Long seckllId) {
        //删除用户redis缓存,
        redisTemplate.boundHashOps("seckillOrder").delete(name);
        //从缓存中查询秒杀商品,设置库存加1
        SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.boundHashOps("seckillGoods").get(seckllId);
        seckillGoods.setStockCount(seckillGoods.getStockCount()+1);
        //存回缓存中
        redisTemplate.boundHashOps("seckillOrder").put(seckllId,seckillGoods);
    }
}
