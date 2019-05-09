package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillGoodsQuery;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.Date;
import java.util.List;

/**
 * Created by wang on 2019/5/9.
 */
@Service
@Transactional
public class SeckillGoodsServiceImpl implements SeckillGoodsService{
    @Autowired
    private SeckillGoodsDao seckillGoodsDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IdWorker idWorker;
    @Override
    public List<SeckillGoods> findList() {
        List<SeckillGoods> seckillGoodsList = redisTemplate.boundHashOps("seckillGoods").values();
        if (seckillGoodsList == null || seckillGoodsList.size()<=0){
            SeckillGoodsQuery seckillGoodsQuery = new SeckillGoodsQuery();
            SeckillGoodsQuery.Criteria criteria = seckillGoodsQuery.createCriteria();
            criteria.andStatusEqualTo("1");
            criteria.andStockCountGreaterThan(0);
            criteria.andStartTimeLessThanOrEqualTo(new Date());//开始时间小于等于当前时间
            criteria.andEndTimeGreaterThan(new Date());//结束时间大于当前时间
            seckillGoodsList = seckillGoodsDao.selectByExample(seckillGoodsQuery);
            for (SeckillGoods seckillGoods : seckillGoodsList) {
                redisTemplate.boundHashOps("seckillGoods").put(seckillGoods.getId().toString(),seckillGoods);
            }
        }
        return seckillGoodsList;
    }

    @Override
    public SeckillGoods findOneFromRedis(Long id) {
        return (SeckillGoods) redisTemplate.boundHashOps("seckillGoods").get(id.toString());
    }

}
