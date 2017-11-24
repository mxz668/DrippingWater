package com.xz.dripping.common.utils.redis;

import com.xz.dripping.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 功能: 业务编码工具类
 * 创建: liuchongguang - liuchongguang@zillionfortune.com
 * 日期: 2017/1/10 0010 14:27
 * 版本: V1.0
 */
@Component
public class BusinessCodeUtils {

    /**
     * 日志记录器
     */
    private static Logger logger = LoggerFactory.getLogger(BusinessCodeUtils.class);

    @Autowired
    private RedisSequence redisSequence;

    /**
     * 资产编号前缀
     */
    public static final String SEQUENCE_PREFIX = "AMS_ASSET_SEQ:";

    /**
     * 年月格式化
     */
    public static final String MONTH_FORMAT = "yyyyMM";
    public static final String DATE_FORMAT = "yyMMdd";
    public static final String CURRENT_DATE_FORMAT = "yyyyMMddHHmmss";

    /**
     * 生成业务编码
     *
     * @param codePrefix
     * @return
     */
    public String getBusinessCode(String codePrefix, String channel) {
        String businessCode = null;
        String yearMonth = DateUtils.format(DateUtils.now(), MONTH_FORMAT);
        String currentDate = DateUtils.format(DateUtils.now(), DATE_FORMAT);
        //序列名称,前缀加年月 AMA + 1701
        StringBuffer sn = new StringBuffer();
        sn.append(codePrefix).append(yearMonth);
        String sequenceName = sn.toString();
        long sequence = this.getSequence(sequenceName);
        StringBuffer sb = new StringBuffer();
        sb.append(codePrefix).append(channel).append(currentDate).append(String.format("%09d", sequence));
        businessCode = sb.toString();
        return businessCode;
    }

    /**
     * @param sequenceName
     * @return
     * 基于redis获取主键
     */
    private long getSequence(String sequenceName) {
        Long sequence = redisSequence.incr(SEQUENCE_PREFIX+sequenceName);
        if (sequence == null) {
            throw new RuntimeException("获取序列号异常");
        }
        logger.debug("---------------------sequenceName:{},sequence:{}------------------------", sequenceName, sequence);
        return sequence;
    }

}
