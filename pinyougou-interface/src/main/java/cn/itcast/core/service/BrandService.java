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
    void save(Brand brand);
    PageResult search(Integer pageNum, Integer pageSize, Brand brand);
    List<Map> selectOptionList();
    void updateStatus(Long[] ids, String status);
    void saveBeans(ArrayList<Brand> brands);
}
