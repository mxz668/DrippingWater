package com.xz.dripping.controller;

import com.xz.dripping.facade.dto.req.sms.SendMessegeRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by MABIAO on 2017/6/5 0005.
 */
@RestController
@RequestMapping(value = "sendMessegeService")
public class SendMessegeController {

    /**
     * 发送短信
     * @return
     */
    @RequestMapping(value = "sendMessege",method = RequestMethod.POST)
    public String sendTest(@RequestBody SendMessegeRequest req){

        return "";
    }
}
