package cn.itcast.core.service;

import cn.itcast.core.dao.item.ItemCatDao;
import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.item.ItemCatNew;
import cn.itcast.core.pojo.item.ItemCatQuery;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by wang on 2019/4/12.
 */
@Service
@Transactional
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private ItemCatDao itemCatDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public List<ItemCat> findByParentId(Long parentId) {
        ItemCatQuery itemCatQuery = new ItemCatQuery();
        ItemCatQuery.Criteria criteria = itemCatQuery.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<ItemCat> itemCats = itemCatDao.selectByExample(itemCatQuery);
        for (ItemCat itemCat : itemCats) {
            redisTemplate.boundHashOps("itemCatMap").put(itemCat.getName(),itemCat.getTypeId());
        }
        return itemCats;
    }

    @Override
    public ItemCat findOne(Long id) {
        return null;
    }

    @Override
    public void update(ItemCat itemCat) {

    }

    @Override
    public void add(ItemCat itemCat) {

    }


    @Override
    public List<ItemCat> findAll() {
        List<ItemCat> itemCats = itemCatDao.selectByExample(null);

        return itemCats;
    }
    @Override
    public List<ItemCatNew> findApply1() {
        return  itemCatDao.findByParentId(Long.parseLong("0"));
    }

    @Override
    public ItemCatNew findApply2(String name2) {
        System.out.println(name2);

        ItemCatNew byName = itemCatDao.findByName(name2);

        return   byName;
    }

    @Override
    public void addApply1(String name1) {
        ItemCatNew byName = new ItemCatNew();
        byName.setParentId(0L);
        byName.setItemStatus("0");
        byName.setName(name1);
         itemCatDao.addItem(byName);
        System.out.println("添加成功");
    }

    @Override
    public void addApply2(String name1, String name2) {
        ItemCatNew byName = itemCatDao.findByName(name1);
        ItemCatNew itemCatNew = new ItemCatNew();
        itemCatNew.setParentId(byName.getId());
        itemCatNew.setItemStatus("0");
        itemCatNew.setName(name2);
        itemCatDao.addItem(itemCatNew);
        System.out.println("添加成功");

    }




}
