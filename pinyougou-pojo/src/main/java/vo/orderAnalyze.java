package vo;

import java.io.Serializable;

public class orderAnalyze implements Serializable {
    private Long toDayOrderCount;

    private double toDayOrderPrice;
    private Long monthOrderCount;
    private double monthOrderPrice;

    public orderAnalyze() {
    }

    public orderAnalyze(Long toDayOrderCount, double toDayOrderPrice, Long monthOrderCount, double monthOrderPrice) {
        this.toDayOrderCount = toDayOrderCount;
        this.toDayOrderPrice = toDayOrderPrice;
        this.monthOrderCount = monthOrderCount;
        this.monthOrderPrice = monthOrderPrice;
    }

    public Long getToDayOrderCount() {
        return toDayOrderCount;
    }

    public void setToDayOrderCount(Long toDayOrderCount) {
        this.toDayOrderCount = toDayOrderCount;
    }

    public double getToDayOrderPrice() {
        return toDayOrderPrice;
    }

    public void setToDayOrderPrice(double toDayOrderPrice) {
        this.toDayOrderPrice = toDayOrderPrice;
    }

    public Long getMonthOrderCount() {
        return monthOrderCount;
    }

    public void setMonthOrderCount(Long monthOrderCount) {
        this.monthOrderCount = monthOrderCount;
    }

    public double getMonthOrderPrice() {
        return monthOrderPrice;
    }

    public void setMonthOrderPrice(double monthOrderPrice) {
        this.monthOrderPrice = monthOrderPrice;
    }
}
