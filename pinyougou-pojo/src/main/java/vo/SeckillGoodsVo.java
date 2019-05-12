package vo;

import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.good.GoodsDesc;
import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.seckill.SeckillGoods;

import java.io.Serializable;
import java.util.List;

public class SeckillGoodsVo implements Serializable {

    private Goods goods;
    private GoodsDesc goodsDesc;
    private Item item;
    private List<Item> itemList;
    private SeckillGoods seckillGoods;

    public GoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(GoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public SeckillGoodsVo() {
    }

    public SeckillGoodsVo(Goods goods, Item item, List<Item> itemList, SeckillGoods seckillGoods) {
        this.goods = goods;
        this.item = item;
        this.itemList = itemList;
        this.seckillGoods = seckillGoods;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public SeckillGoods getSeckillGoods() {
        return seckillGoods;
    }

    public void setSeckillGoods(SeckillGoods seckillGoods) {
        this.seckillGoods = seckillGoods;
    }

    @Override
    public String toString() {
        return "SeckillGoodsVo{" +
                "goods=" + goods +
                ", item=" + item +
                ", itemList=" + itemList +
                ", seckillGoods=" + seckillGoods +
                '}';
    }
}
