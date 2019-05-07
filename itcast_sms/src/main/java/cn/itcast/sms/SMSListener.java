package cn.itcast.sms;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by wang on 2019/4/25.
 */
@Component
public class SMSListener {
    @Autowired
    private Environment environment;
    @JmsListener(destination = "sms")
    public void sendSMS(Map<String,String> map){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", environment.getProperty("accessKeyId"), environment.getProperty("accessKeySecret"));
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", map.get("PhoneNumbers"));//"18646013242"
        request.putQueryParameter("SignName", map.get("SignName"));//"品优购"
        request.putQueryParameter("TemplateCode", map.get("TemplateCode"));//"SMS_164267131"
        request.putQueryParameter("TemplateParam", map.get("TemplateParam"));//"{\"number\":\"123456\"}"

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
