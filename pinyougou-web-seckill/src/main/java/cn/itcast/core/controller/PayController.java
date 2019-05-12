package cn.itcast.core.controller;

import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.service.SeckillOrderService;
import cn.itcast.core.service.WeixinPayService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wang on 2019/5/10.
 */
/**
 * 支付控制层
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Reference
    private WeixinPayService weixinPayService;

    @Reference
    private SeckillOrderService seckillOrderService;

    /**
     * 生成二维码
     * @return
     */
    @RequestMapping("/createNative")
    public Map createNative(){
        //获取当前用户
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        return weixinPayService.createNative(username);
    }
    //查询订单状态
    @RequestMapping("/queryPayStatus")
    public Result queryPayStatus(String out_trade_no) {
        //获取当前用户
        String userId=SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            int x = 0;
            while (true){
                Map map = weixinPayService.queryPayStatus(out_trade_no);
                if(map==null){
                    return new Result(false,"支付失败");
                }else{
                    if("NOTPAY".equals(map.get("trade_state"))){
                        //修改订单状态
                        Thread.sleep(3000);
                        x++;
                        if (x>200){
                            return new Result(false,"超时");
                        }
                    }else{
                        seckillOrderService.saveOrderFromRedisToDb(userId, Long.valueOf(out_trade_no), (String)map.get("transaction_id"));
                        return new  Result(true, "支付成功");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"支付失败");
        }

    }
}
