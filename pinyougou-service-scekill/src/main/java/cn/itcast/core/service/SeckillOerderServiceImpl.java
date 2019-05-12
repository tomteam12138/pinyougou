package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.Date;

/**
 * Created by wang on 2019/5/9.
 */
@Service
@Transactional
public class SeckillOerderServiceImpl implements SeckillOrderService{
    @Autowired
    private SeckillGoodsDao seckillGoodsDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IdWorker idWorker;

    @Override
    public void submitOrder(Long seckillId, String userId) {
        SeckillGoods seckillGoods = (SeckillGoods) redisTemplate.boundHashOps("seckillGoods").get(seckillId.toString());
        if (seckillGoods==null){
            throw new RuntimeException("商品不存在");
        }
        if (seckillGoods.getStockCount()<=0){
            throw new RuntimeException("商品已抢购一空");
        }
        seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
        redisTemplate.boundHashOps("seckillGoods").put(seckillId.toString(),seckillGoods);
        if (seckillGoods.getStockCount()==0){
            seckillGoodsDao.updateByPrimaryKeySelective(seckillGoods);
            redisTemplate.boundHashOps("seckillGoods").delete(seckillId.toString());

        }

        long orderId = idWorker.nextId();
        SeckillOrder seckillOrder=new SeckillOrder();
        seckillOrder.setId(orderId);
        seckillOrder.setCreateTime(new Date());
        seckillOrder.setMoney(seckillGoods.getCostPrice());//秒杀价格
        seckillOrder.setSeckillId(seckillId);
        seckillOrder.setSellerId(seckillGoods.getSellerId());
        seckillOrder.setUserId(userId);//设置用户ID
        seckillOrder.setStatus("0");//状态
        redisTemplate.boundHashOps("seckillOrder").put(userId, seckillOrder);
    }
}
