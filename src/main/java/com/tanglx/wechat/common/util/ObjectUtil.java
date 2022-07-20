package com.tanglx.wechat.common.util;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * @Describe
 * @Author tanglingxiao
 * @Date 2022-05-26
 */
public class ObjectUtil extends BeanUtils {

    /**
     * @param sources
     * @param target
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> List<T> copyList(List<S> sources, Supplier<T> target) {
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T t = target.get();
            copyProperties(source, t);
            list.add(t);
        }
        return list;
    }

    /**
     * @param sources
     * @param target
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> List<T> copyListNotNull(List<S> sources, Supplier<T> target) {
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T t = target.get();
            copyPropertiesNotNull(source, t);
            list.add(t);
        }
        return list;
    }

    public static <S, T> T copyPropertiesNotNull(S source, Supplier<T> target) {
        T t = target.get();
        copyPropertiesNotNull(source, t);
        return t;
    }

    /**
     * @param source
     * @param target
     * @throws BeansException
     */
    public static void copyPropertiesNotNull(Object source, Object target) throws BeansException {
        copyProperties(source, target, null, (String[]) null);
    }

    /**
     * @param source
     * @param target
     * @param editable
     * @param ignoreProperties
     * @throws BeansException
     */
    private static void copyProperties(Object source, Object target, Class<?> editable, String... ignoreProperties) throws BeansException {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass()
                        .getName() + "] not assignable to Editable class [" + editable.getName() + "]");
            }
            actualEditable = editable;
        }
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            // 判断被复制的属性是否为null, 如果不为null才复制
                            if (value != null) {
                                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                    writeMethod.setAccessible(true);
                                }
                                writeMethod.invoke(target, value);
                            }
                        } catch (Throwable ex) {
                            throw new FatalBeanException("Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }
}
