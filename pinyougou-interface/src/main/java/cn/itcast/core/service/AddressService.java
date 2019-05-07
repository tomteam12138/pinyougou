package cn.itcast.core.service;

import cn.itcast.core.pojo.address.Address;

import java.util.List;

/**
 * Created by wang on 2019/4/29.
 */
public interface AddressService {
    List<Address> findListByLoginUser(String name);
}
