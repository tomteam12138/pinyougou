package cn.itcast.core.service;

import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.item.ItemCatNew;
import entity.PageResult;

import java.util.List;
import java.util.Map;

/**
 * Created by wang on 2019/4/12.
 */
public interface ItemCatService {
    List<ItemCatNew> findByParentId(Long parentId);

    void updateStatus(Long[] ids, String status);

    PageResult search(Integer page, Integer rows, ItemCatNew itemCatNew);

    ItemCatNew findOne(Long id);
}
