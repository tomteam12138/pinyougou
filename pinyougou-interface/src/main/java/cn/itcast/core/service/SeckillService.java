package cn.itcast.core.service;

import cn.itcast.core.pojo.seckill.SeckillOrder;
import entity.PageResult;

public interface SeckillService {

    PageResult seckillorder(Integer page, Integer rows);
}
