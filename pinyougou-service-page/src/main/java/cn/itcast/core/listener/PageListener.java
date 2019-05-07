package cn.itcast.core.listener;

import cn.itcast.core.service.StaticFTLService;
import org.springframework.beans.factory.annotation.Autowired;
import sun.management.counter.perf.PerfInstrumentation;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by wang on 2019/4/25.
 */
public class PageListener implements MessageListener{
    @Autowired
    private StaticFTLService staticFTLService;
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            String id = textMessage.getText();
            //把审核后的商品生成静态化页面
            staticFTLService.staticPage(Long.parseLong(id));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
