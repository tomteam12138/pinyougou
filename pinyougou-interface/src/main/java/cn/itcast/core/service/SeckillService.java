package cn.itcast.core.service;

import cn.itcast.core.pojo.seckill.SeckillOrder;
<<<<<<< HEAD
import entity.PageResult;

public interface SeckillService {

    PageResult seckillorder(Integer page, Integer rows);
	
	    SeckillOrder findSeckillOrder(String name);

    void delSeckill(String name, Long seckllId);

}
