package org.smart4j.chapter2.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * String工具类
 * Created by alvin on 2016/3/12.
 */
public final class StringUtil {

    /**
     * 判断s是否不是空(非空返回true)
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s){
        return !isEmpty(s);
    }

    /**
     * 判断s是否为空（为空返回true）
     * @param s
     * @return
     */
    public static boolean isEmpty(String s){
        if(s != null){
            s = s.trim();
        }
        return StringUtils.isEmpty(s);
    }
}
