package cn.itcast.core.controller;

import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.service.CartService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.CookieUtil;
import vo.Cart;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2019/4/28.
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Reference
    private CartService cartService;
    @RequestMapping("/addGoodsToCartList")
    @CrossOrigin(origins = {"http://localhost:9006"},allowCredentials = "true")
    public Result addGoodsToCartList(Long itemId, Integer num,
                                     HttpServletRequest request, HttpServletResponse response){
        try {
            String cookieValue = CookieUtil.getCookieValue(request, "cart", "utf-8");
            List<Cart> cartList = JSONArray.parseArray(cookieValue, Cart.class);
            Cart newCart = cartService.addGoodsToCart(itemId,num);
            if (cartList!=null && cartList.size()!=0){
                int oldIndex = cartList.indexOf(newCart);
                if (oldIndex != -1){
                    Cart oldCart = cartList.get(oldIndex);
                    List<OrderItem> orderItemList = oldCart.getOrderItemList();
                    int orderItemIndex = orderItemList.indexOf(newCart.getOrderItemList().get(0));
                    if (orderItemIndex != -1){
                        OrderItem orderItem = orderItemList.get(orderItemIndex);
                        orderItem.setNum(orderItem.getNum()+num);
                        orderItem.setTotalFee(new BigDecimal(orderItem.getPrice().doubleValue()*orderItem.getNum()));
                    }else {
                        orderItemList.addAll(newCart.getOrderItemList());
                    }
                }else {
                    cartList.add(newCart);
                }
            }else {
                cartList = new ArrayList<>();
                cartList.add(newCart);
            }
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!"anonymousUser".equals(name)){
                //登录
              cartService.addCartToRedis(cartList,name);
                Cookie cookie = new Cookie("cart","");
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }else {
                //未登录
                String encode = URLEncoder.encode(JSON.toJSONString(cartList), "utf-8");
                Cookie cookie = new Cookie("cart",encode);
                cookie.setMaxAge(24*60*60*7);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            return new Result(true,"添加购物车成功");
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(false,"添加购物车失败");
        }
    }

    @RequestMapping("/findCartList")
    public List<Cart> findCartList(HttpServletRequest request){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String cart = CookieUtil.getCookieValue(request, "cart", "utf-8");
        List<Cart> cartList = JSONArray.parseArray(cart, Cart.class);
        if ("anonymousUser".equals(name)){
            return cartList;
        }else {
            if (cartList == null){
                cartList = new ArrayList<>();
            }
            return cartService.findCartListByRedis(cartList,name);
        }
    }
}
