package cn.itcast.core.service;

import cn.itcast.core.dao.good.BrandDao;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.BrandQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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

    //查询全部品牌 带分页
    @Override
    public PageResult findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page brand = (Page) brandDao.selectByExample(null);
        return new PageResult(brand.getTotal(),brand);
    }

    //查询全部品牌 不带分页
    @Override
    public List<Brand> findAll() {
        return brandDao.selectByExample(null);
    }
    //添加新品牌
    @Override
    public void addBrand(Brand brand) {
        brandDao.insertSelective(brand);
    }
    //按主键查询brand
    @Override
    public Brand findById(Long id) {
        return brandDao.selectByPrimaryKey(id);
    }
    //修改brand
    @Override
    public void updateBrand(Brand brand) {
        brandDao.updateByPrimaryKeySelective(brand);
    }
    //批量删除品牌
    @Override
    public void delete(Long[] ids) {
        if (ids!=null && ids.length!=0){
            for (Long id : ids) {
                brandDao.deleteByPrimaryKey(id);
            }
        }
    }

    @Override
    public PageResult search(Integer pageNum, Integer pageSize, Brand brand) {
        PageHelper.startPage(pageNum,pageSize);
        BrandQuery brandQuery = new BrandQuery();
        BrandQuery.Criteria criteria = brandQuery.createCriteria();
        if (brand.getName()!=null && !"".equals(brand.getName().trim())){
            criteria.andNameLike("%"+brand.getName()+"%");
        }else if(brand.getFirstChar()!=null && "".equals(brand.getFirstChar().trim())){
            criteria.andFirstCharLike(brand.getFirstChar());
        }
        Page<Brand> brands = ( Page<Brand>)brandDao.selectByExample(brandQuery);
        return new PageResult(brands.getTotal(),brands.getResult());
    }

    @Override
    public List<Map> selectOptionList() {
        return brandDao.selectOptionList();
    }
}
