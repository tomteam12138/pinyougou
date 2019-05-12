package cn.itcast.core.service;

import cn.itcast.core.pojo.seckill.SeckillGoods;

import java.util.List;

/**
 * Created by wang on 2019/5/9.
 */
public interface SeckillGoodsService {
   List<SeckillGoods> findList();
   SeckillGoods findOneFromRedis(Long id);
}
