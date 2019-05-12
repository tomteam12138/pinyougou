package cn.itcast.core.service;

import java.util.Map;

public interface PayService {
    Map<String,String> createNative(String name);

    Map<String,String> queryPayStatus(String out_trade_no);

    Map<String, String> closePay(String out_trade_no);
}