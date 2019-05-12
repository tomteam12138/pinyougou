package cn.itcast.core.service;

import cn.itcast.core.dao.good.BrandDao;
import cn.itcast.core.dao.good.GoodsDao;
import cn.itcast.core.dao.good.GoodsDescDao;
import cn.itcast.core.dao.item.ItemCatDao;
import cn.itcast.core.dao.item.ItemDao;
import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.dao.seller.SellerDao;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.good.GoodsDesc;
import cn.itcast.core.pojo.good.GoodsQuery;
import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.item.ItemQuery;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.order.OrderItemQuery;
import cn.itcast.core.pojo.order.OrderQuery;
import com.alibaba.dubbo.config.annotation.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SolrDataQuery;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Transactional;
import vo.GoodsVo;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by wang on 2019/4/13.
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private GoodsDescDao goodsDescDao;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private ItemCatDao itemCatDao;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private SellerDao sellerDao;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination topicPageAndSolrDestination;
    @Autowired
    private Destination queueSolrDeleteDestination;
    @Override
    public void add(GoodsVo vo) {
        Goods goods = vo.getGoods();
        goods.setAuditStatus("0");
        goodsDao.insertSelective(goods);
        GoodsDesc goodsDesc = vo.getGoodsDesc();
        goodsDesc.setGoodsId(goods.getId());
        goodsDescDao.insertSelective(goodsDesc);
        List<Item> itemList = vo.getItemList();

        if ("1".equals(goods.getIsEnableSpec())){
            for (Item item : itemList) {
                //标题  商品名称 + 空格 + 规格1 + 空格 + 规格 2 + 空格 规格 3
                String spec = item.getSpec();
                String title = goods.getGoodsName()+"";
                Map<String,String> specMap = JSON.parseObject(spec, Map.class);
                Set<Map.Entry<String, String>> entries = specMap.entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    title+=entry.getValue()+" ";
                }
                item.setTitle(title);
                setAttribute(item,vo);
                itemDao.insertSelective(item);
            }
        } else {
            //不启用 赠品 商品详情页面  没有规格选择  一个规格 颜色随机 默认库存数据
            Item item = new Item();

            //标题==商品名称
            item.setTitle(vo.getGoods().getGoodsName());
            //给库存对象设置属性
            setAttribute(item, vo);
            //价格  默认值
            item.setPrice(new BigDecimal(0));
            //库存 默认
            item.setNum(0);
            //默认启用
            item.setStatus("1");
            //默认是否
            //item.setIsDefault("0");
            //保存库存表
            itemDao.insertSelective(item);
        }

    }

    @Override
    public PageResult search(Integer page, Integer rows, Goods goods) {
        PageHelper.startPage(page,rows);
        GoodsQuery goodsQuery = new GoodsQuery();
        GoodsQuery.Criteria criteria = goodsQuery.createCriteria();
        criteria.andIsDeleteIsNull();
        if (goods.getAuditStatus()!=null){
            criteria.andAuditStatusEqualTo(goods.getAuditStatus());
        }
        if(goods.getGoodsName()!=null && !"".equals(goods.getGoodsName().trim())){
            criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
        }
        if (goods.getSellerId()!=null){
            criteria.andSellerIdEqualTo(goods.getSellerId());
        }

        Page<Goods> goods1 = (Page<Goods>)goodsDao.selectByExample(goodsQuery);
        return new PageResult(goods1.getTotal(),goods1.getResult());
    }

    @Override
    public GoodsVo findOne(Long id) {
        GoodsVo goodsVo = new GoodsVo();
        goodsVo.setGoods(goodsDao.selectByPrimaryKey(id));
        goodsVo.setGoodsDesc(goodsDescDao.selectByPrimaryKey(id));
        ItemQuery itemQuery = new ItemQuery();
        ItemQuery.Criteria criteria = itemQuery.createCriteria();
        criteria.andGoodsIdEqualTo(id);
        goodsVo.setItemList(itemDao.selectByExample(itemQuery));
        return goodsVo;
    }

    @Override
    public void delete(Long[] ids) {
        Goods goods = new Goods();
        goods.setIsDelete("1");
        if (ids!=null && ids.length!=0){
            for (Long id : ids) {
                goods.setId(id);
                goodsDao.updateByPrimaryKeySelective(goods);
                //把审核通过后的商品,把商品的id发送至消息中间件activeMQ中,放入消息队列,采用点对点模式
                jmsTemplate.send(queueSolrDeleteDestination, new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createTextMessage(String.valueOf(id));
                    }
                });
            }
        }
    }

    @Override
    public void update(GoodsVo vo) {
        Goods goods = vo.getGoods();
        goodsDao.updateByPrimaryKeySelective(goods);
        goodsDescDao.updateByPrimaryKeySelective(vo.getGoodsDesc());
        List<Item> itemList = vo.getItemList();
        ItemQuery itemQuery = new ItemQuery();
        ItemQuery.Criteria criteria = itemQuery.createCriteria();
        criteria.andGoodsIdEqualTo(goods.getId());
        itemDao.deleteByExample(itemQuery);
            if ("1".equals(goods.getIsEnableSpec())){
                for (Item item : itemList) {
                    //标题  商品名称 + 空格 + 规格1 + 空格 + 规格 2 + 空格 规格 3
                    String spec = item.getSpec();
                    String title = goods.getGoodsName()+"";
                    Map<String,String> specMap = JSON.parseObject(spec, Map.class);
                    Set<Map.Entry<String, String>> entries = specMap.entrySet();
                    for (Map.Entry<String, String> entry : entries) {
                        title+=entry.getValue()+" ";
                    }
                    item.setTitle(title);
                    setAttribute(item,vo);
                    itemDao.insertSelective(item);
                }
            } else {
                //不启用 赠品 商品详情页面  没有规格选择  一个规格 颜色随机 默认库存数据
                Item item = new Item();

                //标题==商品名称
                item.setTitle(vo.getGoods().getGoodsName());
                //给库存对象设置属性
                setAttribute(item, vo);
                //价格  默认值
                item.setPrice(new BigDecimal(0));
                //库存 默认
                item.setNum(0);
                //默认启用
                item.setStatus("1");
                //默认是否
                //item.setIsDefault("0");
                //保存库存表
                itemDao.insertSelective(item);
            }

    }

    @Override
    public void updateStatus(Long[] ids, String status) {
        Goods goods = new Goods();
        goods.setAuditStatus(status);
        if (ids!=null && ids.length!=0){
            for (Long id : ids) {
                goods.setId(id);
                goodsDao.updateByPrimaryKeySelective(goods);
                //把审核通过后的商品,把商品的id发送至消息中间件activeMQ中,放入消息队列,采用发布订阅模式
                jmsTemplate.send(topicPageAndSolrDestination, new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createTextMessage(String.valueOf(id));
                    }
                });

            }
        }

    }

    public void setAttribute(Item item,GoodsVo vo){
        item.setSellPoint(vo.getGoods().getCaption());
        String itemImages = vo.getGoodsDesc().getItemImages();
        List<Map> maps = JSON.parseArray(itemImages, Map.class);
        Map map = maps.get(0);
        String url = (String)map.get("url");
        item.setImage(url);
        item.setCategoryid(vo.getGoods().getCategory3Id());
        item.setStatus("1");
        item.setCreateTime(new Date());
        item.setUpdateTime(new Date());
        item.setGoodsId(vo.getGoods().getId());
        item.setSellerId(vo.getGoods().getSellerId());
        ItemCat itemCat = itemCatDao.selectByPrimaryKey(vo.getGoods().getCategory3Id());
        item.setCategory(itemCat.getName());
        Brand brand = brandDao.selectByPrimaryKey(vo.getGoods().getBrandId());
        item.setBrand(brand.getName());
        item.setSeller(sellerDao.selectByPrimaryKey(vo.getGoods().getSellerId()).getNickName());
    }

}
