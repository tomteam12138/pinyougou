package cn.itcast.core.service;

import cn.itcast.core.dao.good.BrandDao;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.BrandQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wang on 2019/4/6.
 */
@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandDao brandDao;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination topicPageAndSolrDestination;

    //添加新品牌
    @Override
    public void save(Brand brand) {
        brandDao.insert(brand);
    }

    @Override
    public PageResult search(Integer pageNum, Integer pageSize, Brand brand) {
        PageHelper.startPage(pageNum,pageSize);
        BrandQuery brandQuery = new BrandQuery();
        BrandQuery.Criteria criteria = brandQuery.createCriteria();
        if (brand.getName()!=null && !"".equals(brand.getName().trim())){
            criteria.andNameLike("%"+brand.getName()+"%");
        }

        if(brand.getFirstChar()!=null && !"".equals(brand.getFirstChar().trim())){
            criteria.andFirstCharLike(brand.getFirstChar());
        }

        if(brand.getStatus()!=null && !"".equals(brand.getStatus().trim())){
            criteria.andStatusEqualTo(brand.getStatus());
        }
        Page<Brand> brands = ( Page<Brand>)brandDao.selectByExample(brandQuery);
        return new PageResult(brands.getTotal(),brands.getResult());
    }

    @Override
    public List<Map> selectOptionList() {
        return brandDao.selectOptionList();
    }

    @Override
    public void updateStatus(Long[] ids, String status) {
        Brand brand = new Brand();
        brand.setStatus(status);
        if (ids!=null && ids.length!=0){
            for (Long id : ids) {
                brand.setId(id);
                brandDao.updateByPrimaryKeySelective(brand);
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

    @Override
    public void saveBeans(ArrayList<Brand> brands) {
        if(brands!=null && brands.size()>0){
            for (Brand brand : brands) {
                brandDao.insertSelective(brand);
            }
        }

    }
}
