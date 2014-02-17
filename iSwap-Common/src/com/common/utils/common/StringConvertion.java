/*
 * @(#)StringConvertion.java
 *
 * Copyright (C) 2005, zgcworld All right reserved.
 * see the site: http://www.zgcworld.com
 */

package com.common.utils.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 字符串操作类
 * @author lifh
 * @mail wslfh2005@163.com
 * @since Jan 20, 2011 4:20:01 PM
 * @name com.common.utils.common.StringConvertion.java
 * @version 1.0
 */

public class StringConvertion {

    /**
     * LOG used to output the Log info..
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * string 数组 转成 long 数组 
     * @param strs
     * @return
     * @author lifh
     */
    public static Long[] convertionToLong(String[] strs) {// 将String数组转换为Long类型数组
        List<String> list = Arrays.asList(strs);
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).trim().equals("")){
                list.remove(i);
            }
        }
        Long[] longs = new Long[list.size()]; // 声明long类型的数组
        for (int i = 0; i < list.size(); i++) {
            String str = strs[i]; // 将strs字符串数组中的第i个值赋值给str
            long thelong = Long.valueOf(str);// 将str转换为long类型，并赋值给thelong
            longs[i] = thelong;// 将thelong赋值给 longs数组中对应的地方
        }

        return longs; // 返回long数组
    }

    /**
     * 字符串转换为long数组 ,如 11,2,3 转为[11,2,33]
     * @param str
     * @param separ 分隔符
     * @return
     * @author lifh
     */
    public static Long[] convertionToLongs(String str,String separ){
        String[] strs = str.split(separ);
        ArrayList<Long> list = new ArrayList<Long>();
        for (String string : strs) {
            if(!string.trim().equals("")){
                list.add(Long.valueOf(string));
            }
        }
        Long[] longs = new Long[list.size()];
       return list.toArray(longs);
        
    }
}
