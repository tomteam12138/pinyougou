package cn.itcast.core.service;

import cn.itcast.core.pojo.seckill.SeckillOrder;

public interface SeckillService {

    SeckillOrder findSeckillOrder(String name);

    void delSeckill(String name, Long seckllId);
}
