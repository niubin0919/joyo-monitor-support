/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.dao.base;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.joyotime.net.monitor.exchange.dao.model.buffer.BufferFtp;
import com.joyotime.net.monitor.exchange.dao.model.buffer.BufferSql;
import com.joyotime.net.monitor.exchange.dao.mysql.mapper.RedisBufferMapper;
import com.joyotime.net.monitor.exchange.dao.redis.util.RedisUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 消费buffer
 * @author nbin
 * @version $Id: BufferPull.java, v 0.1 2019年1月16日 下午5:17:19 nbin Exp $
 */
@Slf4j
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BaseBufferPull {

    @Resource
    private RedisBufferMapper redisBufferMapper;

    /**
     * pull
     * @return
     */
    public List<String> pullBuffer() {
        List<String> keys = new ArrayList<String>();
        List<String> args = new ArrayList<String>();
        args.add(UUID.randomUUID().toString().replaceAll("\\-", ""));
        List<String> result = new ArrayList<String>();
        try {
            String data = (String) RedisUtil.ExeRedisScrpit(RedisUtil.GetConsumerScript(BaseBufferPush.QueueKeyName, BaseBufferPush.QueueTempKeyName), keys, args);
            if (data != null) {
                int iindex = data.indexOf('|');
                if (iindex > -1) {
                    result.add(data.substring(0, iindex));
                    result.add(data.substring(iindex + 1));
                }
            }
        } catch (Exception e) {
            log.error("pull consumer queue error:" + e.getMessage(), e);
        }

        return result;
    }
    
    /**
     * 上传ftp 或者insert mysql
     * @param msg
     * @return
     */
    public int writeData(String msg){
        int oknum = 0;
        //sql
        if (msg.indexOf("insert") == 0) {
            try {
                oknum = redisBufferMapper.insertBuffer(new BufferSql(msg)); 
            } catch (Exception e) {
                oknum = -1;
                log.error("BufferPull-writeData sql:" + msg + "\n" + e.getMessage(), e);
            }
        }else{
            try {
                BufferFtp ftp = JSON.parseObject(msg, BufferFtp.class);
                //TODO ftp上传文件
            } catch (Exception e) {
                oknum = -1;
                log.error("BufferPull-writeData ftp:" + msg + "\n" + e.getMessage(), e);
            }
        }
        return oknum;
    }

}
