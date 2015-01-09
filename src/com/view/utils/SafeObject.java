package com.view.utils;

/**
 * 安全对象
 * @author yinfx
 * @time 15-1-9 上午9:19
 */
public class SafeObject {
    /**
     * 判断传入对象是否非空
     * @param o 传入对象
     * @return 返回true表示该对象非空，反之该对象为空
     */
    public static boolean isNotNull(Object o){
        return o != null ? o.toString().trim().length() > 0 : false;
    }
    /**
     * 判断传入对象是否为空
     * @param o 传入对象
     * @return 返回true表示该对象为空，反之该对象非空
     */
    public static boolean isNull(Object o){
        return !isNotNull(o);
    }
}
