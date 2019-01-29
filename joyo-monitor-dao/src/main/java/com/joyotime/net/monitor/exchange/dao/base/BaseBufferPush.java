/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.dao.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.joyotime.net.monitor.exchange.dao.redis.util.RedisUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 缓冲buffer
 * @author nbin
 * @version $Id: BufferPush.java, v 0.1 2019年1月15日 下午7:00:40 nbin Exp $
 */
@Getter
@Setter
@Slf4j
public abstract class BaseBufferPush {
    
    /** 缓冲buffer key名称*/
    public String              bufferName;
    /** 缓冲buffer长度，用于分片*/
    public int                 bufferSize       = 100;
    /** 拼装前缀*/
    public String              prefix;
    /** 缓冲buffer前缀*/
    private String             bufferPrefixName = "buffer_";
    /** 消费队列*/
    public static final String QueueKeyName     = "buffer_consumerQueue";
    /** 消费队列临时队列*/
    public static final String QueueTempKeyName = "buffer_consumerQueueTemp";
    
    /** 获得缓冲内容  */
    public boolean valuesBody(String ms){
        return insertRedisBuffer(this.appendBody(ms));
    }
    /**
     * 上传内容转换为相应buffer
     * @param msg
     * @return
     */
    public abstract String appendBody(String msg);
    
    /**
     * insert
     * @param buffer
     * @return
     */
    private boolean insertRedisBuffer(String buffer){
        boolean append = false;
        if (StringUtils.isBlank(buffer)) {
            return append;
        }
        boolean sd = exeData(buffer);
        if (sd) {
            log.info("exe "+getBufferName()+" success!");
            append = true;
        } else {
            append = false;
            log.info("exe "+getBufferName()+" error!");
        }
        return append;
    }
    
    /** 
     * exe script
     * @param data
     * @return
     */
    public boolean exeData(String data) {
        List<String> keys = new ArrayList<String>();
        List<String> args = new ArrayList<String>();
        args.add(data);
        String str = null;
        try {
            str = RedisUtil.ExeRedisScrpit(getAppendScript(), keys, args).toString();
            if (str == null) {
                str = "";
            }
            log.info(getBufferName() + "push buffer success：" + str);
        } catch (Exception e) {
            log.error(getBufferName() + "push buffer error：" + e.getMessage(), e);
        }
        return str.equals("1") || str.equals("2");
    }
    
    private String getAppendScript() {
        return RedisUtil.GetAppendScript(getBufferName(), getPrefix(), QueueKeyName, getBufferSize());
    }
    
    public void setBufferName(String bufferName) {
        this.bufferName = bufferPrefixName + bufferName;
    }

    public void setBufferSize(int bufferSize) {
        if (bufferSize < 0) {
            bufferSize = 100;
        }
        this.bufferSize = bufferSize;
    }
}
