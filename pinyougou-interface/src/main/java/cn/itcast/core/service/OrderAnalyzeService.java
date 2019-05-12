package cn.itcast.core.service;

import vo.orderAnalyze;

import java.util.Date;

public interface OrderAnalyzeService {
    orderAnalyze findAll(Date startTime, Date endTime,Date beginDayOfMonth,Date endDayOfMonth);
}
