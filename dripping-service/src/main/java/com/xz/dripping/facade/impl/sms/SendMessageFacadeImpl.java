package com.xz.dripping.facade.impl.sms;

import com.xz.dripping.facade.dto.req.sms.SendMessageRequest;
import com.xz.dripping.facade.service.sms.SendMessageFacade;
import com.xz.dripping.service.sms.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by MABIAO on 2017/6/5 0005.
 */
@Service
public class SendMessageFacadeImpl implements SendMessageFacade {

    @Autowired
    private SendMessageService sendMessageService;

    @Override
    public String sendMessage(SendMessageRequest req){
        try{
            sendMessageService.sendMessage(req);
        }catch (Exception e){

        }
        return null;
    }
}
