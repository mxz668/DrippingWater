package com.xz.dripping.controller.sms;

import com.aliyun.mns.client.CloudAccount;
import com.xz.dripping.facade.dto.req.sms.SendMessageRequest;
import com.xz.dripping.facade.service.sms.SendMessageFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by MABIAO on 2017/6/5 0005.
 */
@RestController
@RequestMapping(value = "sendMessageService")
public class SendMessageController {

    @Autowired
    private SendMessageFacade sendMessageFacade;

    /**
     * 发送短信
     * @return
     */
    @RequestMapping(value = "sendMessage",method = RequestMethod.POST)
    public String sendMessage(@RequestBody SendMessageRequest req){
        String ret = sendMessageFacade.sendMessage(req);
        return ret;
    }
}
