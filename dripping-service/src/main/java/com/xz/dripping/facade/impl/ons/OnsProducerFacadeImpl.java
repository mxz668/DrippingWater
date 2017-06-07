package com.xz.dripping.facade.impl.ons;

import com.xz.dripping.facade.service.ons.OnsProducerFacade;
import com.xz.dripping.service.ons.OnsProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by MABIAO on 2017/6/6 0006.
 */
@Service
public class OnsProducerFacadeImpl implements OnsProducerFacade {

    @Autowired
    private OnsProducerService onsProducerService;

    @Override
    public String pushMessage() {
        try {
            onsProducerService.pushMessage();
        }catch (Exception e){

        }
        return null;
    }
}
