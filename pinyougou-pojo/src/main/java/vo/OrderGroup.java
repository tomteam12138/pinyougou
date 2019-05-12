package vo;

import java.io.Serializable;

public class OrderGroup implements Serializable {
    private String sellerId;
    private Integer num;

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
