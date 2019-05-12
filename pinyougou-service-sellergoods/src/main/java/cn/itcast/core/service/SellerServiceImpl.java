package cn.itcast.core.service;

import cn.itcast.core.dao.seller.SellerDao;
import cn.itcast.core.pojo.seller.Seller;
import cn.itcast.core.pojo.seller.SellerQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.Date;

/**
 * Created by wang on 2019/4/12.
 */
@Service
//@Transactional
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerDao sellerDao;
    @Override
    public void add(Seller seller) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(seller.getPassword());
        seller.setPassword(encode);
        seller.setStatus("0");
        seller.setCreateTime(new Date());
        sellerDao.insertSelective(seller);
    }

    @Override
    public Seller findOne(String username) {
        return sellerDao.selectByPrimaryKey(username);
    }

    @Override
    public PageResult search(Integer page, Integer rows, Seller seller) {
        PageHelper.startPage(page,rows);
        SellerQuery sellerQuery = new SellerQuery();
        SellerQuery.Criteria criteria = sellerQuery.createCriteria();
        criteria.andStatusEqualTo(seller.getStatus());
        if (seller.getName()!=null && !"".equals(seller.getName().trim())){
            criteria.andNameLike("%"+seller.getName()+"%");
        }else if(seller.getNickName()!=null && !"".equals(seller.getNickName().trim())){
            criteria.andNickNameLike("%"+seller.getNickName()+"%");
        }
        Page<Seller> sellerList = ( Page<Seller>)sellerDao.selectByExample(sellerQuery);
        return new PageResult(sellerList.getTotal(),sellerList.getResult());
    }

    @Override
    public void updateStatus(Seller seller) {
        sellerDao.updateByPrimaryKeySelective(seller);
    }

}
