package cn.itcast.core.service;

import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.item.Item;
import entity.PageResult;
import vo.GoodsVo;

import java.util.Map;

/**
import vo.GoodsVo;
import vo.SeckillGoodsVo;

/**
 * Created by wang on 2019/4/13.
 */
public interface GoodsService {

    void add(GoodsVo vo);

    PageResult search(Integer page, Integer rows, Goods goods);

    GoodsVo findOne(Long id);

    void delete(Long[] ids);

    void update(GoodsVo vo);

    void updateStatus(Long[] ids, String status);
}
