package com.xz.dripping.common.utils;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Velocity工厂
 * Created by MABIAO on 2017/6/9 0009.
 */
public class VelocityFactory {

    Logger logger = LoggerFactory.getLogger(VelocityFactory.class);

    public VelocityFactory(){

    }

    /**
     * 初始化
     */
    public void init(){
        try{
            Velocity.init();
        }catch (Exception e){
            logger.info("Velocity 初始化失败!",e);
        }
    }

    /**
     * Velocity 上下文
     * @return
     */
    public VelocityContext getContext(){
        VelocityContext context = new VelocityContext();
        return context;
    }
}
