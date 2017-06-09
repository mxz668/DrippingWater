package com.xz.dripping.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bean - 工具类
 */
public final class BeanUtils {
    private BeanUtils() {

    }

    private static final Logger LOG = LoggerFactory.getLogger(BeanUtils.class);

    /*
     * 用来缓存BeanCopier的缓存
     */
    private static final ConcurrentHashMap<String, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap();

    private static BeanCopier getBeanCopier(Class<?> sourceClass, Class<?> destClass) {
        String key = sourceClass.getCanonicalName() + ":" + destClass.getCanonicalName();
        BeanCopier beanCopier = BEAN_COPIER_CACHE.get(key);
        if (beanCopier == null) {
            beanCopier = BeanCopier.create(sourceClass, destClass, false);
            BEAN_COPIER_CACHE.putIfAbsent(key, beanCopier);
        }
        return beanCopier;
    }

    /**
     * 复制某个对象为目标对象类型的对象 当source与target对象属性名相同, 但数据类型不一致时，source的属性值不会复制到target对象
     *
     * @param <T>      目标对象类型参数
     * @param source   源对象
     * @param destType 目标对象类型
     * @return 复制后的结果对象
     */
    public static <T> T copyAs(Object source, Class<T> destType) throws Exception {
        if (source == null || destType == null) {
            return null;
        }
        try {
            BeanCopier beanCopier = getBeanCopier(source.getClass(), destType);
            T dest = destType.newInstance();
            beanCopier.copy(source, dest, null);
            return dest;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 复制源对象集合到目标对象列表
     *
     * @param source   源对象
     * @param destType 目标对象
     * @param <T>      源对象类型参数
     * @param <K>      目标对象类型参数
     * @return 结果集合, 一个list
     */
    public static <T, K> List<K> copyAs(Collection<T> source, Class<K> destType) throws Exception {
        if (CollectionUtils.isNullOrEmpty(source) || destType == null) {
            return Collections.EMPTY_LIST;
        }

        List<K> result = new ArrayList<K>();
        if (source.isEmpty()) {
            return result;
        }
        try {
            for (Object object : source) {
                BeanCopier beanCopier = getBeanCopier(object.getClass(), destType);
                K dest = destType.newInstance();
                beanCopier.copy(object, dest, null);
                result.add(dest);
            }
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    /**
     * 复制属性：从源对象复制和目标对象相同的属性
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copy(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }
        BeanCopier beanCopier = getBeanCopier(source.getClass(), target.getClass());
        beanCopier.copy(source, target, null);
    }

    /**
     * 复制属性：从源对象复制和目标对象相同的属性，除了忽略的属性之外
     * 如果属性名相同，但数据类型不同，会抛出运行时异常FatalBeanException
     *
     * @param source           源对象
     * @param target           目标对象
     * @param ignoreProperties 忽略属性
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    /**
     * 将Map对象拷贝到Bean，Map中的key对应Bean的属性名，value对应属性值
     *
     * @param source 源对象，map
     * @param target 目标对象
     */
    public static void copyMapToObject(Map<?, ?> source, Object target) throws Exception {
        try {
            org.apache.commons.beanutils.BeanUtils.populate(target, source);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 设置属性
     *
     * @param bean  目标对象
     * @param name  属性名
     * @param value 属性值
     */
    public static void setProperty(Object bean, String name, Object value) throws Exception {
        try {
            org.apache.commons.beanutils.BeanUtils.setProperty(bean, name, value);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 获取属性的值
     *
     * @param bean 目标对象
     * @param name 属性名
     * @return 属性的值，其实是String类型
     */
    public static Object getProperty(Object bean, String name) throws Exception {
        try {
            return org.apache.commons.beanutils.BeanUtils.getProperty(bean, name);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 设置Field值
     *
     * @param bean      要设置对象
     * @param fieldName 字段名
     * @param value     值
     */
    public static void setFieldValue(Object bean, String fieldName, Object value) throws Exception {
        try {
            Field field = findField(bean.getClass(), fieldName);
            field.setAccessible(true);
            field.set(bean, value);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 取得指定名称的Field, 子类找不到, 去父类里找
     *
     * @param clz       类
     * @param fieldName 指定名称
     * @return 找不到返回null
     */
    public static Field findField(Class<?> clz, String fieldName) {
        Field f = null;
        try {
            f = clz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            if (clz.getSuperclass() != null) {
                f = findField(clz.getSuperclass(), fieldName);
            }
            if (LOG.isTraceEnabled()) {
                LOG.trace(e.getMessage(), e);
            }
        }
        return f;
    }

    /**
     * 用于model修改时的对象复制,把srcModel复制到destModel,srcModel中为null的字段不复制，同名且类型相同的属性才复制
     *
     * @param srcModel  表单提交的源对象
     * @param destModel 数据库中的目标对象
     */
    public static void copyNotNullProperties(Object srcModel, Object destModel) throws Exception {
        if (srcModel == null || destModel == null) {
            return;
        }

        try {
            PropertyDescriptor[] srcDescriptors = Introspector.getBeanInfo(srcModel.getClass()).getPropertyDescriptors();
            PropertyDescriptor[] destDescriptors = Introspector.getBeanInfo(destModel.getClass()).getPropertyDescriptors();
            Map<String, PropertyDescriptor> destPropertyNameDescriptorMap = new HashMap<String, PropertyDescriptor>();
            for (PropertyDescriptor destPropertyDescriptor : destDescriptors) {
                destPropertyNameDescriptorMap.put(destPropertyDescriptor.getName(), destPropertyDescriptor);
            }
            for (PropertyDescriptor srcDescriptor : srcDescriptors) {
                PropertyDescriptor destDescriptor = destPropertyNameDescriptorMap.get(srcDescriptor.getName());
                if (destDescriptor != null && destDescriptor.getPropertyType() == srcDescriptor.getPropertyType()
                        && destDescriptor.getPropertyType() != Class.class) {// 类型相同的属性才复制
                    if (srcDescriptor.getReadMethod() != null) {
                        Object val = srcDescriptor.getReadMethod().invoke(srcModel);
                        if (val != null && destDescriptor.getWriteMethod() != null) {// not null
                            destDescriptor.getWriteMethod().invoke(destModel, val);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 适用于copy非集合类的属性，主要适用场景，源对象和目标对象有相同名称且为集合类的属性
     * 使用该方法存在的问题：对于集合类的属性，即使类型相同，也没有办法copy成功
     * 添加该方法的原因：对于集合类，在运行期间会丢失类型信息
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyNonCollectionProperties(Object source, Object target) {
        List<String> ignoreProperties = new ArrayList<String>();
        Field[] declaredFields = target.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (Collection.class.isAssignableFrom(field.getType())
                    || Map.class.isAssignableFrom(field.getType())) {
                ignoreProperties.add(field.getName());
            }
        }
        org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties.toArray(new String[ignoreProperties.size()]));
    }
}