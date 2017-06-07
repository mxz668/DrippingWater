package com.xz.dripping.common.mq.listener;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.xz.dripping.common.dto.BaseResponse;
import com.xz.dripping.facade.dto.req.ons.OnsProducerRequest;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 消费者监听器
 * Created by MABIAO on 2017/6/6 0006.
 */
public class OnsListener implements MessageListener {

    /**
     * 日志记录器
     */
    private static Logger logger = LoggerFactory.getLogger(OnsListener.class);

    @Override
    public Action consume(Message message, ConsumeContext context) {
        try {
            byte msg[] = message.getBody();
            String msgBody = new String(message.getBody(), "UTF-8");
            JSONObject jsonObject = JSONObject.fromObject(msgBody);

            //包含列表 转换
//            Map<String, Class> map = new HashMap<String, Class>();
//            map.put("changeAssetProductModels", ChangeAssetProductModel.class);
//            ChangeAssetProductRelationRequest req = (ChangeAssetProductRelationRequest) JSONObject.toBean(jsonObject, ChangeAssetProductRelationRequest.class, map);

            OnsProducerRequest req = (OnsProducerRequest)JSONObject.toBean(jsonObject);

            logger.info("请求参数:"+msgBody);
//            BaseResponse response = assetProductRelationService.changeAssetProductRelation(req);
//            String res = JsonUtils.beanToJson(response);
//            logger.info("TA资产匹配请求返回参数:"+res);
        } catch (Exception e) {
            e.printStackTrace();
            return Action.ReconsumeLater;
        }
        return Action.CommitMessage;
    }
}
