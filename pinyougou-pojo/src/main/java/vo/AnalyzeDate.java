package vo;

import java.io.Serializable;

public class AnalyzeDate implements Serializable {

    private Integer num;
    private Integer three;
    private Integer seven;

    public AnalyzeDate() {
    }

    public AnalyzeDate(Integer num, Integer three, Integer seven) {
        this.num = num;
        this.three = three;
        this.seven = seven;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getThree() {
        return three;
    }

    public void setThree(Integer three) {
        this.three = three;
    }

    public Integer getSeven() {
        return seven;
    }

    public void setSeven(Integer seven) {
        this.seven = seven;
    }

    @Override
    public String toString() {
        return "AnalyzeDate{" +
                "num=" + num +
                ", three=" + three +
                ", seven=" + seven +
                '}';
    }
}
