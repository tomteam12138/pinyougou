package cn.itcast.core.service;

import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.pojo.order.OrderItem;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import vo.OrderGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrderItemDao orderItemDao;

    @Override
    public Map<String, List> findPic() {
        List<OrderGroup> orderGroupList = orderItemDao.selectGroupBySellerId();
        ArrayList<String> sellerList = new ArrayList<>();
        ArrayList<Integer> numList = new ArrayList<>();
        HashMap<String, List> hashMap = new HashMap<>();
        for (OrderGroup orderGroup : orderGroupList) {
            String sellerId = orderGroup.getSellerId();
            sellerList.add(sellerId);
            Integer num = orderGroup.getNum();
            numList.add(num);
        }
        hashMap.put("seller", sellerList);
        hashMap.put("num", numList);

        return hashMap;
    }

    @Override
    public List<Map> findPic2() {
        ArrayList<Map> list = new ArrayList<>();
        List<OrderGroup> orderGroupList = orderItemDao.selectGroupBySellerId();
        List<OrderItem> orderItems = orderItemDao.selectByExample(null);
        double count = orderItems.size();
        for (OrderGroup orderGroup : orderGroupList) {
            HashMap<String, Object> hashMap = new HashMap<>();
            Double num = orderGroup.getNum()/count*100;
            num = (double) Math.round(num * 100) / 100;
            String sellerId = orderGroup.getSellerId();
            hashMap.put("name", sellerId);
            hashMap.put("y", num);
            list.add(hashMap);
        }


        return list;
    }
}
