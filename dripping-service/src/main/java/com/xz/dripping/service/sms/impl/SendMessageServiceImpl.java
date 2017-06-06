package com.xz.dripping.service.sms.impl;

import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.BatchSmsAttributes;
import com.aliyun.mns.model.MessageAttributes;
import com.aliyun.mns.model.RawTopicMessage;
import com.aliyun.mns.model.TopicMessage;
import com.xz.dripping.common.sms.CloudAccountFactory;
import com.xz.dripping.facade.dto.req.sms.SendMessageRequest;
import com.xz.dripping.service.sms.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 短信发送接口实现
 * Created by MABIAO on 2017/6/5 0005.
 */
@Service
public class SendMessageServiceImpl implements SendMessageService {

    @Autowired
    private CloudAccountFactory cloudAccountFactory;

    @Override
    public String sendMessage(SendMessageRequest req) throws Exception {
        MNSClient mnsClient = cloudAccountFactory.getMNSClient();
        CloudTopic topic = mnsClient.getTopicRef("sms.topic-cn-hangzhou");

        /**
         * Step 2. 设置SMS消息体（必须）
         * 注：目前暂时不支持消息内容为空，需要指定消息内容，不为空即可。
         */
        RawTopicMessage msg = new RawTopicMessage();
        msg.setMessageBody(req.getSendContent());

        /**
         * Step 3. 生成SMS消息属性
         */
        MessageAttributes messageAttributes = new MessageAttributes();
        BatchSmsAttributes batchSmsAttributes = new BatchSmsAttributes();
        // 3.1 设置发送短信的签名（SMSSignName）
        batchSmsAttributes.setFreeSignName("抛物线");
        // 3.2 设置发送短信使用的模板（SMSTempateCode）
        batchSmsAttributes.setTemplateCode("SMS_53830360");
        // 3.3 设置发送短信所使用的模板中参数对应的值（在短信模板中定义的，没有可以不用设置）
        BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();
        smsReceiverParams.setParam("name", "语文");
        // 3.4 增加接收短信的号码
        batchSmsAttributes.addSmsReceiver(req.getPhoneNum(), smsReceiverParams);
        messageAttributes.setBatchSmsAttributes(batchSmsAttributes);
        try {
            /**
             * Step 4. 发布SMS消息
             */
            TopicMessage ret = topic.publishMessage(msg, messageAttributes);
            System.out.println("MessageId: " + ret.getMessageId());
            System.out.println("MessageMD5: " + ret.getMessageBodyMD5());
        } catch (ServiceException se) {
            System.out.println(se.getErrorCode() + se.getRequestId());
            System.out.println(se.getMessage());
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            mnsClient.close();
        }
        return null;
    }
}
