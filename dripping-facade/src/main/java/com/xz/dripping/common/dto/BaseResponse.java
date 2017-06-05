package com.xz.dripping.common.dto;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 功能: RPC响应基类
 * 创建: Created by MABIAO on 2017/6/5 0005.
 * 日期: 2017/3/27 0027 14:51
 * 版本: V1.0
 */
@SuppressWarnings("unchecked")
public class BaseResponse implements Serializable {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 2588960438551328273L;

    /**
     * 业务响应码
     */
    private String respCode = "0000";

    /**
     * 业务响应描述
     */
    private String respMsg = "成功";

    /**
     * 附加信息
     */
    private String addition = "";

    /**
     * 默认构造
     */
    public BaseResponse() {
    }

    /**
     * 重载构造
     *
     * @param respCode 业务响应码
     * @param respMsg  业务响应描述
     */
    public BaseResponse(String respCode, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
    }

    /**
     * 重载构造
     *
     * @param respCode 业务响应码
     * @param respMsg  业务响应描述
     * @param addition 附加信息
     */
    public BaseResponse(String respCode, String respMsg, String addition) {
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.addition = addition;
    }

    /**
     * 构建RPC响应对象
     *
     * @param clazz 类
     * @param <T>   RPC响应对象类型
     * @return RPC响应对象
     */
    public static <T extends BaseResponse> T build(Class clazz) {
        Object object = null;
        try {
            object = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) object;
    }

    /**
     * 构建RPC响应对象
     *
     * @return RPC响应对象
     */
    public static BaseResponse build() {
        return new BaseResponse();
    }

    /**
     * 构建RPC响应对象
     *
     * @param respCode 业务响应码
     * @param respMsg  业务响应描述
     * @return RPC响应对象
     */
    public static BaseResponse build(String respCode, String respMsg) {
        return new BaseResponse(respCode, respMsg);
    }

    /**
     * 构建RPC响应对象
     *
     * @param respCode 业务响应码
     * @param respMsg  业务响应描述
     * @param addition 附加信息
     * @return RPC响应对象
     */
    public static BaseResponse build(String respCode, String respMsg, String addition) {
        return new BaseResponse(respCode, respMsg, addition);
    }

    /**
     * 构建RPC响应对象
     *
     * @param clazz    类
     * @param respCode 业务响应码
     * @param respMsg  业务响应描述
     * @param <T>      RPC响应对象类型
     * @return RPC响应对象类型
     */
    public static <T extends BaseResponse> T build(Class clazz, String respCode, String respMsg) {
        Object object = null;
        try {
            object = clazz.newInstance();
            invokeMethod(clazz, "setRespCode", new Class[]{String.class}, object, respCode);
            invokeMethod(clazz, "setRespMsg", new Class[]{String.class}, object, respMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) object;
    }

    /**
     * 构建RPC响应对象
     *
     * @param clazz    类
     * @param respCode 业务响应码
     * @param respMsg  业务响应描述
     * @param addition 附加信息
     * @param <T>      RPC响应对象类型
     * @return RPC响应对象类型
     */
    public static <T extends BaseResponse> T build(Class clazz, String respCode, String respMsg, String addition) {
        Object object = null;
        try {
            object = clazz.newInstance();
            invokeMethod(clazz, "setRespCode", new Class[]{String.class}, object, respCode);
            invokeMethod(clazz, "setRespMsg", new Class[]{String.class}, object, respMsg);
            invokeMethod(clazz, "setAddition", new Class[]{String.class}, object, addition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) object;
    }

    /**
     * 反射调用类方法
     *
     * @param clazz      类
     * @param methodName 方法名称
     * @param classes    方法参数类型
     * @param object     调用对象
     * @param objects    方法参数
     * @return 调用对象
     */
    private static Object invokeMethod(Class clazz, String methodName, final Class[] classes, Object object, Object... objects) {
        try {
            Method method = getMethod(clazz, methodName, classes);
            if (method != null) {
                method.invoke(object, objects);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 反射获取类方法
     *
     * @param clazz      类
     * @param methodName 方法名称
     * @param classes    方法参数类型
     * @return 方法对象
     */
    private static Method getMethod(Class clazz, String methodName, final Class[] classes) {
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodName, classes);
        } catch (NoSuchMethodException e) {
            try {
                method = clazz.getMethod(methodName, classes);
            } catch (NoSuchMethodException ex) {
                if (clazz.getSuperclass() != null) {
                    method = getMethod(clazz.getSuperclass(), methodName, classes);
                }
            }
        }
        return method;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }

    public boolean isSuccess() {
        return this.respCode.equals("0000");
    }
}
