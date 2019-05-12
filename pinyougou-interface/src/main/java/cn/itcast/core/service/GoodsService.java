package cn.itcast.core.service;

import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.item.Item;
import entity.PageResult;
import vo.GoodsVo;
import vo.SeckillGoodsVo;

/**
 * Created by wang on 2019/4/13.
 */
public interface GoodsService {



    PageResult search(Integer page, Integer rows, Goods goods);

    GoodsVo findOne(Long id);

    void delete(Long[] ids);


    void updateStatus(Long[] ids, String status);

    SeckillGoodsVo findById(Long id);

    void addSeckill(SeckillGoodsVo vo);

    Item findByitemId(Long id);
}
