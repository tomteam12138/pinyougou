package cn.itcast.core.service;

import java.util.Map;

/**
 * Created by wang on 2019/5/10.
 */
public interface WeixinPayService {
    Map<String, String> createNative(String name);
    Map<String, String> queryPayStatus(String out_trade_no);
}
