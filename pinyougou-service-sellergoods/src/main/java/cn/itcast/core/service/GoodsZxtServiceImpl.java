package cn.itcast.core.service;

import cn.itcast.core.dao.good.GoodsDao;
import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.good.GoodsQuery;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.order.OrderItemQuery;
import cn.itcast.core.pojo.order.OrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class GoodsZxtServiceImpl implements GoodsZxtService {
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Override
    public Map<String, Object> find(String sellerId) {
       // 1)按sellerId查询goods,返回所有商品分类,id及名字;
        GoodsQuery goodsQuery=new GoodsQuery();
        goodsQuery.createCriteria().andSellerIdEqualTo(sellerId);
        List<Goods> goodsList = goodsDao.selectByExample(goodsQuery);
        // 2)按sellerId及支付状态查询订单,获取订单id通过订单id查询,查询订单详情
        //3)遍历goods,定义总数量totalnum;
        //嵌套遍历订单详情,判断GoodsId,若相同,订单详情的数量
        // 赋值给总数量,封装到map集合Map<goodsNAme,totalnum>
        OrderQuery orderQuery=new OrderQuery();
        orderQuery.createCriteria().andSellerIdEqualTo(sellerId).andStatusEqualTo("1");
        List<Order> orderList = orderDao.selectByExample(orderQuery);
        //定义Map 封装商品及对应的数量
        Map<String,Object> map=new HashMap<>();
        List<String> goodsNameList=new ArrayList<>();
        List<Integer> countList=new ArrayList<>();
        for (Goods goods : goodsList) {
            goodsNameList.add(goods.getGoodsName());
            int totalNum=0;
              for (Order order : orderList) {
            OrderItemQuery orderItemQuery=new OrderItemQuery();
            orderItemQuery.createCriteria().andOrderIdEqualTo(order.getOrderId());
            List<OrderItem> orderItemList = orderItemDao.selectByExample(orderItemQuery);
                  for (OrderItem orderItem : orderItemList) {
                      if (goods.getId().equals(orderItem.getGoodsId())){
                          totalNum+=orderItem.getNum();
                      }
                  }
            }
            countList.add(totalNum);
            map.put("name",goodsNameList);
              map.put("list",countList);
              //重新赋值为0
//              totalNum=0;
        }
        return map;
    }
}
