package cn.itcast.core.service;

import cn.itcast.core.pojo.good.Brand;
import entity.PageResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wang on 2019/4/6.
 */
public interface BrandService {
    //查找全部品牌
    List<Brand> findAll();
    //查找全部品牌带分页
    PageResult findPage(Integer pageNum,Integer pageSize);
    //添加新品牌
    void addBrand(Brand brand);
    //按主键查找品牌
    Brand findById(Long id);
    //修改品牌信息
    void updateBrand(Brand brand);

    void delete(Long[] ids);

    PageResult search(Integer pageNum, Integer pageSize, Brand brand);

    List<Map> selectOptionList();

    void updateStatus(Long[] ids, String status);

    void saveBeans(ArrayList<Brand> brands);

}
