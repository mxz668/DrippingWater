package com.xz.dripping.facade.service.sms;

import com.xz.dripping.facade.dto.req.sms.SendMessageRequest;

/**
 * 发送短信facade
 * Created by MABIAO on 2017/6/5 0005.
 */
public interface SendMessageFacade {

    /**
     * 发送短信
     * @return
     */
    public String sendMessage(SendMessageRequest req);
}
