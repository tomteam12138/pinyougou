package cn.itcast.core.service;

import cn.itcast.core.pojo.seller.Seller;
import entity.PageResult;

/**
 * Created by wang on 2019/4/12.
 */
public interface SellerService {
    void add(Seller seller);

    Seller findOne(String username);

    PageResult search(Integer page, Integer rows, Seller seller);

    void updateStatus(Seller seller);

}
