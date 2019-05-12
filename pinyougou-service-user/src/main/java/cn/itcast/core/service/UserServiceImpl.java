package cn.itcast.core.service;

import cn.itcast.core.dao.address.AddressDao;
import cn.itcast.core.dao.user.UserDao;
import cn.itcast.core.pojo.address.Address;
import cn.itcast.core.pojo.address.AddressQuery;
import cn.itcast.core.pojo.user.User;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by wang on 2019/4/26.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination smsDestination;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AddressDao addressDao;
    @Override
    public void sendCode(String phone) {
        jmsTemplate.send(smsDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                String randomNumeric = RandomStringUtils.randomNumeric(6);
                redisTemplate.boundValueOps(phone).set(randomNumeric,1, TimeUnit.HOURS);
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("PhoneNumbers",phone);
                mapMessage.setString("SignName","品优购");
                mapMessage.setString("TemplateCode","SMS_164267131");
                mapMessage.setString("TemplateParam","{\"number\":\""+randomNumeric+"\"}");
                return mapMessage;
            }
        });
    }

    @Override
    public void add(String smscode, User user) {
        String code = (String) redisTemplate.boundValueOps(user.getPhone()).get();
        if (code!=null){
            if (smscode.equals(code)){
                user.setCreated(new Date());
                user.setUpdated(new Date());
                userDao.insertSelective(user);
            }else {
                throw new RuntimeException("验证码不正确,请重新请求");
            }
        }else {
            throw  new RuntimeException("验证码已失效,请重新请求");
        }
    }

    @Override
    public List<Address> selectAddress(String name) {
        AddressQuery addressQuery = new AddressQuery();
        addressQuery.createCriteria().andUserIdEqualTo(name);
      return   addressDao.selectByExample(addressQuery);
    }

    @Override
    public void addAddress(Address address) {
        addressDao.insertSelective(address);
    }

    @Override
    public void updateAddress(Address address) {
        addressDao.updateByPrimaryKeySelective(address);
    }

    @Override
    public void deleteAddress(Long id) {
        addressDao.deleteByPrimaryKey(id);
    }

    @Override
    public Address findOneAddress(Long id) {
        return addressDao.selectByPrimaryKey(id);
    }

    @Override
    public User findUserAndRole(String username) {
        return userDao.findUserAndRole(username);
    }

}
