package cn.itcast.core.service;

import cn.itcast.core.dao.item.ItemDao;
import cn.itcast.core.pojo.item.Item;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2019/5/11.
 */
@Service
public class CollectServiceImpl implements CollectService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ItemDao itemDao;
    @Override
    public void addGoodsToCollect(Long itemId, String username) {
        List<Item> collectList = (List<Item>) redisTemplate.boundHashOps("personCollect").get(username);
        if (collectList!=null && collectList.size()!=0){
            Item item = itemDao.selectByPrimaryKey(itemId);
            int index = collectList.indexOf(item);
            if (index==-1){
               collectList.add(item);
            }

            redisTemplate.boundHashOps("personCollect").put(username,collectList);
        }else {
            Item item = itemDao.selectByPrimaryKey(itemId);
            List<Item> collectList1 = new ArrayList<>();
            collectList1.add(item);
            redisTemplate.boundHashOps("personCollect").put(username,collectList1);
        }
    }

    @Override
    public List<Item> showCollect(String username) {
        return (List<Item>) redisTemplate.boundHashOps("personCollect").get(username);

    }
}
