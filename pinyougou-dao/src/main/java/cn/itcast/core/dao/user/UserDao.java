package cn.itcast.core.dao.user;

import cn.itcast.core.pojo.role.Role;
import cn.itcast.core.pojo.user.User;

import java.util.List;

import cn.itcast.core.pojo.user.UserQuery;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
  int countByExample(UserQuery example);

  int deleteByExample(UserQuery example);

  int deleteByPrimaryKey(Long id);

  int insert(User record);

  int insertSelective(User record);

  List<User> selectByExample(UserQuery example);

  User selectByPrimaryKey(Long id);

  int updateByExampleSelective(@Param("record") User record, @Param("example") UserQuery example);

  int updateByExample(@Param("record") User record, @Param("example") UserQuery example);

  int updateByPrimaryKeySelective(User record);

  int updateByPrimaryKey(User record);

  //新增方法
  List<User> findAll();
  User findOne(String username);
  User findUserAndRole(String username);

}