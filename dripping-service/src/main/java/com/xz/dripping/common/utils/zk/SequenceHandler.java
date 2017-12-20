package com.xz.dripping.common.utils.zk;


import com.xz.dripping.common.utils.DateUtils;

/**
 * Created by wangwanbin on 2017/9/5.
 */
public abstract class SequenceHandler {

    /**
     * 获取sequence
     * @param sequenceEnum
     * @return
     */
    public abstract long nextId(SequenceEnum sequenceEnum);

    /**
     * 获取按日统计sequence
     * @return
     */
    public abstract long nextYmdId (final SequenceEnum sequenceEnum,String ymd) throws Exception;

    public abstract String getNodeId(String prefixName);


    /**
     * 生成业务编码
     *
     * @param sequenceEnum
     * @return
     */
    public String getBusinessCode(SequenceEnum sequenceEnum, String channel) {
        String businessCode = null;
        String currentDate = DateUtils.format(DateUtils.now(), DateUtils.ONLY_DATA_FORMAT);
        //序列名称,前缀加年月 AMA + 1701
        long sequence = this.nextId(sequenceEnum);
        StringBuffer sb = new StringBuffer();
        sb.append(channel).append(sequenceEnum.getCode()).append(currentDate).append(String.format("%09d", sequence));
        businessCode = sb.toString();
        return businessCode;
    }
}
