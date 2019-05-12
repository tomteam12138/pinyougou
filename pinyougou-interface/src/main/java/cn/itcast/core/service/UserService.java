package cn.itcast.core.service;

import cn.itcast.core.pojo.address.Address;
import cn.itcast.core.pojo.user.User;

import java.util.List;

/**
 * Created by wang on 2019/4/26.
 */
public interface UserService {
    void sendCode(String phone);

    void add(String smscode, User user);

    List<Address> selectAddress(String name);

    void addAddress(Address address);

    void updateAddress(Address address);

    void deleteAddress(Long id);

    Address findOneAddress(Long id);
}
