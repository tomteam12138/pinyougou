package cn.itcast.core.dao.user;

import vo.UserLog;

import java.util.List;

public interface UserLogDao {
    void addUserLog(UserLog userLog);

    int findAllDate();

    List<UserLog> findAll();

    List<UserLog> findToDay(String startTime, String endTime);
}
