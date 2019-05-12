package cn.itcast.core.service;

import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.item.ItemCatNew;
import entity.PageResult;
import cn.itcast.core.pojo.item.ItemCatNew;

import java.util.List;
import java.util.Map;

/**
 * Created by wang on 2019/4/12.
 */
public interface ItemCatService {
    List<ItemCatNew> findByParentId(Long parentId);

    void updateStatus(Long[] ids, String status);

    PageResult search(Integer page, Integer rows, ItemCatNew itemCatNew);

    void add(ItemCat itemCat);

    List<ItemCat> findAll();

    List<ItemCatNew> findApply1();

     ItemCatNew findApply2(String name2);

    void addApply1(String name1);

    void addApply2(String name1, String name2);


    ItemCatNew findOne(Long id);
}
