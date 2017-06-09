package com.xz.dripping.service.ons.impl;

import com.xz.dripping.common.mq.OnsProducer;
import com.xz.dripping.facade.dto.req.ons.OnsProducerRequest;
import com.xz.dripping.service.ons.OnsProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by MABIAO on 2017/6/6 0006.
 */
@Service
public class OnsProducerServiceImpl implements OnsProducerService {

    @Autowired
//    private OnsProducer onsProducer;

    public String pushMessage() throws Exception{
        OnsProducerRequest request = new OnsProducerRequest();
//        request.setContent("你好");
//        onsProducer.publishMessage("test_tianyu_demo","",request);
        return "";
    }
}
