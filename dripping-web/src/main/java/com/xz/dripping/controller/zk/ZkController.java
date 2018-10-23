package com.xz.dripping.controller.zk;

import com.xz.dripping.common.utils.zk.SequenceEnum;
import com.xz.dripping.common.utils.zk.ZKIncreaseSequenceHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.server.InactiveGroupException;

import javax.annotation.PostConstruct;

/**
 * Created by MABIAO on 2017/9/4.
 */
@RestController
@RequestMapping(value = "/zkService")
public class ZkController {

    private ZKIncreaseSequenceHandler sequenceHandler;


    @PostConstruct
    public void init() {
        sequenceHandler = ZKIncreaseSequenceHandler.getInstance("192.168.0.65", "/sequence/asset", "seq");
    }

    @RequestMapping(value = "/generate",method = RequestMethod.POST)
    public void generateCode(@RequestBody String date){
//        long id = sequenceHandler.nextYmdId(SequenceEnum.ASSET_CODE, date);
//        System.out.println("id:" + id);
        sequenceHandler.getNodeId("");
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

//        System.out.println(System.currentTimeMillis());
        String node = String.format("%02d",1l + 1);
        System.out.println(node);
    }

}
