package com.xz.dripping.service.sms;

import com.xz.dripping.facade.dto.req.sms.SendMessageRequest;

/**
 * Created by MABIAO on 2017/6/5 0005.
 */
public interface SendMessageService {

    public String sendMessage(SendMessageRequest req) throws Exception;
}
