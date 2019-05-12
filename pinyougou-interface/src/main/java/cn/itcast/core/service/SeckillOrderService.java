package cn.itcast.core.service;

import cn.itcast.core.pojo.seckill.SeckillOrder;
import entity.PageResult;

import java.util.List;

public interface SeckillOrderService {
    List<SeckillOrder> findAll(String name);

    PageResult search( Integer page, Integer rows, SeckillOrder seckillOrder);

/**
 * Created by wang on 2019/5/9.
 */
public interface SeckillOrderService {
    void submitOrder(Long seckillId,String userId);

    SeckillOrder searchOrderFromRedisByUserId(String userId);

    void saveOrderFromRedisToDb(String userId,Long orderId,String transactionId);
}
