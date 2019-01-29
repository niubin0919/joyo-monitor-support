package com.joyotime.net.monitor.exchange.utils;
/**
 * 默认值 
 * @author nbin
 * @version $Id: NumberUtils.java, v 0.1 2019年1月17日 下午4:02:26 nbin Exp $
 */
public  class NumberUtils{

    public static <T extends Number> T  getOrDefault(T number , T defaultNum){
        if(number == null){
            return defaultNum;
        }
        return number;
    }
}
