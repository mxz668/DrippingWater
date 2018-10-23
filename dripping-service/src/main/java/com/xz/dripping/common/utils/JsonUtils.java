package com.xz.dripping.common.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * Description:java bean log工具类<br />
 * @version 0.1 2017/1/3
 * @title JsonUtils
 */
public class JsonUtils {

    private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * 随意转换任何类型的数据
     *
     * @param obj
     * @return
     */
    public static String getArgsString(Object obj) {
        if (obj == null) {
            return "";
        }
        Class cls = obj.getClass();
        if (JsonUtils.isCommonType(cls)) {
            return JsonUtils.getCommonTypeValue(cls, obj);
        }
        if (JsonUtils.isListType(cls)) {
            List<?> list = (List<?>) obj;
            return JsonUtils.getListTypeString(list);
        }
        if (JsonUtils.isMapType(cls)) {
            Map<String, Object> map = (Map<String, Object>) obj;
            return JsonUtils.getMapTypeString(map);
        }
        return JsonUtils.beanToJson(obj);
    }

    /**
     * 将javabean转换为xml字符串(支持深层转换)
     *
     * @param obj
     * @return
     */
    public static String beanToJson(Object obj) {

        StringBuilder sb = new StringBuilder();
        try {
            sb.append("{");
            convertJson(obj, sb);
            sb.append("}");
        } catch (Exception e) {
            logger.error("bean转换xml异常", e);
        } finally {
            return sb.toString();
        }
    }

    /**
     * 递归方法,深层遍历bean
     *
     * @param obj
     * @return
     * @throws NoSuchMethodException
     * @throws java.lang.reflect.InvocationTargetException
     * @throws IllegalAccessException
     */
    private static void convertJson(Object obj, StringBuilder sb) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (obj == null) {
            return;
        }

        Method[] methods = obj.getClass().getMethods();
        List<Method> methodList = new ArrayList<>();
        for (Method method : methods) {
            if (subMethodName(method.getName()).startsWith("get") && !subMethodName(method.getName()).equals("getClass")) {
                methodList.add(method);
            }
        }

        for (int i = 0; i < methodList.size(); i++) {
            Method method = methodList.get(i);
            Class<?> cls = method.getReturnType();
            //1.2普通属性
            if (isListType(cls)) {
                //说明该类属性为List,需要继续遍历
                sb.append("\"" + getFieldByMethod(method.getName()) + "\"");
                sb.append(" :");
                List<?> list = (List<?>) method.invoke(obj);
                sb.append(getListTypeString(list));
            } else if (isMapType(cls)) {
                //说明该类属性为Map,需要继续遍历
                sb.append("\"" + getFieldByMethod(method.getName()) + "\"");
                sb.append(" :");
                Map<String, Object> map = (Map<String, Object>) method.invoke(obj);
                sb.append(getMapTypeString(map));
            } else if (isCommonType(cls)) {
                //添加普通属性节点(if判断句添加)
                sb.append(getCommonTypeString(obj, method));
            } else {
                //添加bean节点并递归下去
                Object t1 = method.invoke(obj);
                sb.append("\"" + getFieldByMethod(method.getName()) + "\"");
                sb.append(" : ");
                sb.append("{");
                convertJson(t1, sb);
                sb.append("}");
            }
            //判断是否为最后一个，如果是，则去掉，
            if (i < methodList.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("");

    }

    /**
     * 根据类型判断是否为基本数据类型
     *
     * @param cls
     * @return
     */
    private static boolean isBaseType(Class<?> cls) {
        if (String.class.isAssignableFrom(cls) || Integer.class.isAssignableFrom(cls)
                || java.math.BigDecimal.class.isAssignableFrom(cls) || Boolean.class.isAssignableFrom(cls)
                || Long.class.isAssignableFrom(cls) || Character.class.isAssignableFrom(cls)
                || Short.class.isAssignableFrom(cls) || Byte.class.isAssignableFrom(cls)
                || Float.class.isAssignableFrom(cls) || Double.class.isAssignableFrom(cls)
                || int.class.isAssignableFrom(cls) || boolean.class.isAssignableFrom(cls)
                || long.class.isAssignableFrom(cls) || char.class.isAssignableFrom(cls)
                || short.class.isAssignableFrom(cls) || byte.class.isAssignableFrom(cls)
                || float.class.isAssignableFrom(cls) || double.class.isAssignableFrom(cls)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为日期类型
     *
     * @param cls
     * @return
     */
    private static boolean isDateType(Class<?> cls) {
        if (Date.class.isAssignableFrom(cls)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否属于普通类型(基本数据类型+String+Date)
     *
     * @param cls
     * @return
     */
    public static boolean isCommonType(Class<?> cls) {
        if (isDateType(cls) || isBaseType(cls)) {
            return true;
        }
        return false;
    }

    /**
     * 是否为list类型
     *
     * @param cls
     * @return
     */
    public static boolean isListType(Class<?> cls) {
        if (List.class.isAssignableFrom(cls)) {
            return true;
        }
        return false;
    }

    public static String getListTypeString(List<?> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        try {
            if (list != null && list.size() > 0) {
                for (int j = 0; j < list.size(); j++) {
                    // 添加引用bean节点
                    Object paramType = list.get(j);//增加 基本或日期类型判断
                    if(isBaseType(paramType.getClass()) || isDateType(paramType.getClass())){
                        sb.append("\"");
                        sb.append(getCommonTypeValue(paramType.getClass(), paramType));
                        sb.append("\"");
                    }else{
                        sb.append("{");
                        convertJson(list.get(j), sb);
                        sb.append("}");
                    }
                    if (j < list.size() - 1) {
                        sb.append(",");
                    }
                }
            }
        } catch (Exception e) {
            sb.append("");
            logger.info("list转换为json失败", e);
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 根据类型判断是否为基本数据类型并获取值
     *
     * @param cls
     * @param value
     * @return
     */
    public static String getCommonTypeValue(Class<?> cls, Object value) {
        if (value == null) return "";
        String result = "";
        if (isBaseType(cls)) {
            result = String.valueOf(value);
        } else if (isDateType(cls)) {
            Date dateValue = (Date) value;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
            result = simpleDateFormat.format(dateValue);
        } else {
            return null;
        }
        return result;
    }

    /**
     * 是否为Map类型
     *
     * @param cls
     * @return
     */
    public static boolean isMapType(Class<?> cls) {
        if (Map.class.isAssignableFrom(cls)) {
            return true;
        }
        return false;
    }

    /**
     * 添加普通属性节点
     *
     * @param obj
     * @param method
     * @return
     * @throws java.lang.reflect.InvocationTargetException
     * @throws IllegalAccessException
     */
    private static String getCommonTypeString(Object obj, Method method) throws InvocationTargetException, IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        Type type = method.getReturnType();
        Class<?> cls = (Class<?>) type;
        //1.2普通属性
        Object returnValue = method.invoke(obj);
        String value = getCommonTypeValue(cls, returnValue);
        // 2.添加普通属性节点
        String key = getFieldByMethod(method.getName());
        //添加key
        sb.append("\"" + key + "\"");
        sb.append(" : ");
        //添加value
        if (!"null".equals(value)) {
            sb.append("\"" + value + "\"");
        } else {
            sb.append("\"\"");
        }
        return sb.toString();
    }

    public static String getMapTypeString(Map map) {
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        sb.append("{");
        try {
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                String mapValue = getCommonTypeValue(entry.getValue().getClass(), entry.getValue());
                if (mapValue == null) {
                    // 如果value不是基本类型,继续递归
                    sb.append("{");
                    convertJson(entry.getValue(), sb);
                    sb.append("}");
                } else {
                    //为xml key添加值
                    sb.append("\"" + entry.getKey() + "\"");
                    sb.append(" : ");
                    sb.append("\"" + mapValue + "\"");
                }
                if (iterator.hasNext()) {
                    sb.append(",");
                }
            }
        } catch (Exception e) {
            sb.append("");
            logger.info("map转换为json失败", e);
        }
        sb.append("}");
        return sb.toString();
    }


    public static void main(String[] args) {
/*        Data data = new Data();
        Trade trade = new Trade();
        trade.setType("V");
        trade.setCurrency("RMB");
        trade.setID("10086");
        Card card = new Card();
        card.setPhone("18721976137");
        card.setIdType("身份证");
        card.setName("王万彬");
        card.setSign("99999999999");
        Men men1 = new Men();
        men1.setAge(15);
        men1.setFriend("周杰伦");
        Men men2 = new Men();
        men2.setAge(16);
        men2.setFriend("张学友");
        List<Men> list = new ArrayList<>();
        list.add(men1);
        list.add(men2);
        card.setMen(list);

        Map<String,Object> map = new HashMap<>();
        map.put("pic1","baautiful111");
        map.put("pic1", "baautiful222");
        Picture picture = new Picture();
        picture.setPictureName("国际油画");
        picture.setPicureeNum("有8幅");
        map.put("pictureMap", picture);

        card.setPicMap(map);
        data.setTrade(trade);
        data.setCard(card);

        beanToXml(data);*/
    }

    /**
     * 截取方法名
     *
     * @param name
     * @return
     */
    private static String subMethodName(String name) {
        name = name.substring(name.lastIndexOf(".") + 1);//最后的方法名
        return name;
    }

    /**
     * 根据方法名获取字段名并首字母小写
     *
     * @param methodName
     * @return
     */
    private static String getFieldByMethod(String methodName) {
        String name = methodName.substring(methodName.lastIndexOf(".") + 1);//最后的方法名
        return name.substring(3, 4).toLowerCase().concat(name.substring(4));
    }


    public static String objectToJson(Object bean) {
        try {
            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(bean);
            return json;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("json convert error", e);
        }
    }

    public static String object2Json(Object obj) {
        return objectToJson(obj);
    }

    public static Map<?, ?> jsonToObject(String json) {
        try {
            ObjectMapper om = new ObjectMapper();
            Map<?, ?> map = om.readValue(json, Map.class);
            return map;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("json convert error", e);
        }
    }

    public static <T> T json2Object(String json, Class<T> clz) {
        try {
            ObjectMapper om = new ObjectMapper();
            T obj = om.readValue(json, clz);
            return obj;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("json convert error", e);
        }
    }


}
