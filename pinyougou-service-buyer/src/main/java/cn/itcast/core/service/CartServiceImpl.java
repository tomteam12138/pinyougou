package cn.itcast.core.service;


import cn.itcast.core.dao.item.ItemDao;
import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.order.OrderItem;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import vo.Cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2019/4/28.
 */
@Service
@Transactional
public class CartServiceImpl implements CartService {
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public Cart addGoodsToCart(Long itemId, Integer num) {
        Item item = itemDao.selectByPrimaryKey(itemId);
        Cart cart = new Cart();
        cart.setSellerId(item.getSellerId());
        cart.setSellerName(item.getSeller());
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setNum(num);
        orderItem.setItemId(itemId);
        orderItem.setGoodsId(item.getGoodsId());
        orderItem.setPicPath(item.getImage());
        orderItem.setPrice(item.getPrice());
        orderItem.setTotalFee(new BigDecimal(item.getPrice().doubleValue()*num));
        orderItem.setTitle(item.getTitle());
        orderItemList.add(orderItem);
        cart.setOrderItemList(orderItemList);
        return cart;
    }

    @Override
    public void addCartToRedis(List<Cart> oldCartList,String name) {
        List<Cart> newCartList = (List<Cart>) redisTemplate.boundHashOps("cart").get(name);
        if (newCartList!=null && newCartList.size()!=0){
            for (Cart cart : oldCartList) {
                int i = newCartList.indexOf(cart);
                if (i != -1){
                    List<OrderItem> oldOrderItemList = cart.getOrderItemList();
                    Cart newCart = newCartList.get(i);
                    List<OrderItem> newOrderItemList = newCart.getOrderItemList();
                    for (OrderItem orderItem : oldOrderItemList) {
                        int j = newOrderItemList.indexOf(orderItem);
                        if (j != -1){
                            OrderItem newOrderItem = newOrderItemList.get(j);
                            newOrderItem.setNum(orderItem.getNum()+newOrderItem.getNum());
                            newOrderItem.setTotalFee(new BigDecimal(newOrderItem.getPrice().doubleValue() * newOrderItem.getNum()));
                        }else {
                            newOrderItemList.add(orderItem);
                        }
                    }
                }else {
                    newCartList.add(cart);
                }
            }
            redisTemplate.boundHashOps("cart").put(name,newCartList);
        }else {
            redisTemplate.boundHashOps("cart").put(name,oldCartList);
        }
    }

    @Override
    public List<Cart> findCartListByRedis(List<Cart> oldCartList, String name) {
        List<Cart> newCartList = (List<Cart>) redisTemplate.boundHashOps("cart").get(name);
        if (newCartList!=null && newCartList.size()!=0){
            if (oldCartList!=null && oldCartList.size()!=0){
                for (Cart cart : oldCartList) {
                    int i = newCartList.indexOf(cart);
                    if (i != -1){
                        List<OrderItem> oldOrderItemList = cart.getOrderItemList();
                        Cart newCart = newCartList.get(i);
                        List<OrderItem> newOrderItemList = newCart.getOrderItemList();
                        for (OrderItem orderItem : oldOrderItemList) {
                            int j = newOrderItemList.indexOf(orderItem);
                            if (j != -1){
                                OrderItem newOrderItem = newOrderItemList.get(j);
                                newOrderItem.setNum(orderItem.getNum()+newOrderItem.getNum());
                                newOrderItem.setTotalFee(new BigDecimal(newOrderItem.getPrice().doubleValue() * newOrderItem.getNum()));
                            }else {
                                newOrderItemList.add(orderItem);
                            }
                        }
                    }else {
                        newCartList.add(cart);
                    }
                }
            }
            return newCartList;
        }else {
           return oldCartList;
        }
    }
}
