package com.xz.dripping.controller.zk;

import com.xz.dripping.common.utils.DateUtils;
import com.xz.dripping.common.utils.redis.BusinessCodeUtils;
import com.xz.dripping.common.utils.zk.SequenceEnum;
import com.xz.dripping.common.utils.zk.ZKIncreaseSequenceHandler;
import com.xz.dripping.service.redis.BaseRedisService;
import com.xz.dripping.service.redis.impl.RedisModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * Created by MABIAO on 2017/9/4.
 */
@RestController
@RequestMapping(value = "/zkService")
public class ZkController {

    private ZKIncreaseSequenceHandler sequenceHandler;

    @PostConstruct
    public void init() {
        sequenceHandler = ZKIncreaseSequenceHandler.getInstance("127.0.0.1", "/sequence/asset", "seq");
    }

    @RequestMapping(value = "/generate",method = RequestMethod.POST)
    public void generateCode(@RequestBody String date){
        long id = sequenceHandler.nextYmdId(SequenceEnum.ASSET_CODE, date);
        System.out.println("id:" + id);
    }

    public static void main(String args[]){
//        int i = 0;
//        while (true){
//            ++i;
//            System.out.println(i);
//            if(i == 5){
//                break;
//            }
//        }

        System.out.println(System.currentTimeMillis());
    }

}
