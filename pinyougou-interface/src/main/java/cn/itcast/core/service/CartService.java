package cn.itcast.core.service;

import vo.Cart;

import java.util.List;

/**
 * Created by wang on 2019/4/28.
 */
public interface CartService {
    Cart addGoodsToCart(Long itemId, Integer num);

    void addCartToRedis(List<Cart> oldCartList,String name);

    List<Cart> findCartListByRedis(List<Cart> cartList, String name);
}
