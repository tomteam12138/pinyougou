package cn.itcast.core.service;

import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.order.OrderItem;

import java.util.List;

/**
 * Created by wang on 2019/5/11.
 */
public interface CollectService {
    void addGoodsToCollect(Long itemId, String username);

    List<Item> showCollect(String username);
}
