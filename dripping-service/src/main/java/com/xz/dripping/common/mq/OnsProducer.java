package com.xz.dripping.common.mq;

import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ONS服务主题消息生产者
 * Created by MABIAO on 2017/6/6 0006.
 */
public class OnsProducer<T> {
    /**
     * 日志记录器
     */
    private static Logger logger = LoggerFactory.getLogger(OnsProducer.class);

    /**
     * 消息生产者
     */
    private ProducerBean producerBean;

    /**
     * 重试次数
     */
    private int retry = 5;

    /**
     * 重试间隔(单位:毫秒)
     */
    private long interval = 200;

    /**
     * 默认消息字符集
     */
    private String charset = "UTF-8";

    /**
     * 发送主题消息
     *
     * @param tag
     * @param messageContent
     * @return
     */
    public boolean publishMessage(String topic, String tag, T messageContent) {
        try {
            byte[] buffer = JSON.toJSONString(messageContent).getBytes(charset);
            Message message = new Message(topic, tag, buffer);
            for (int i = 0; i < this.retry; i++) {
                if (this.publishMessage(topic, tag, message, i)) {
                    return true;
                } else {
                    Thread.sleep(this.interval);
                }
            }
        } catch (Exception e) {
            logger.error("发送主题消息失败", e);
            return false;
        }
        return false;
    }

    private boolean publishMessage(String topic, String tag, Message message, int retry) {
        try {
            SendResult result = producerBean.send(message);
            if (StringUtils.isNotBlank(result.getMessageId())) {
                logger.info("MessageId:{}", result.getMessageId());
                return true;
            } else {
                logger.error("重试发送主题消息失败,次数:{}", retry);
                return false;
            }
        } catch (Exception e) {
            if (retry > 0) {
                logger.error("重试发送主题消息失败,次数:" + retry, e);
            } else {
                logger.error("发送主题消息失败", e);
            }
            return false;
        }
    }

    public ProducerBean getProducerBean() {
        return producerBean;
    }

    public void setProducerBean(ProducerBean producerBean) {
        this.producerBean = producerBean;
    }

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
