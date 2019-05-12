package vo;

import cn.itcast.core.pojo.order.Order;

import java.io.Serializable;
import java.util.List;

public class OrderVo  implements Serializable{
    private List<Order> orderList;
    private String sellerId;

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}
