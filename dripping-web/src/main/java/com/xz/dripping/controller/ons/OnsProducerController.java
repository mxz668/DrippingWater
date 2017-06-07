package com.xz.dripping.controller.ons;

import com.xz.dripping.facade.service.ons.OnsProducerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by MABIAO on 2017/6/6 0006.
 */
@RestController
@RequestMapping(value = "onsProducerService")
public class OnsProducerController {

    @Autowired
    private OnsProducerFacade onsProducerFacade;

    @RequestMapping(value = "pushMessage",method = RequestMethod.POST)
    public void pushMessage(){
        onsProducerFacade.pushMessage();
    }
}
