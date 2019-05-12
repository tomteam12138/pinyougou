package cn.itcast.core.service;

import cn.itcast.core.pojo.seckill.SeckillOrder;
import entity.PageResult;

import java.util.List;

public interface SeckillOrderService {
    List<SeckillOrder> findAll(String name);

    PageResult search( Integer page, Integer rows, SeckillOrder seckillOrder);
}
