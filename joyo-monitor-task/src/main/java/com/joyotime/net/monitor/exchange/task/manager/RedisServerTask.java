/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.task.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.joyotime.net.monitor.exchange.dao.base.BaseBufferPull;
import com.joyotime.net.monitor.exchange.dao.base.BaseBufferPush;
import com.joyotime.net.monitor.exchange.dao.redis.util.RedisUtil;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * 
 * @author nbin
 * @version $Id: RedisServerTask.java, v 0.1 2019年1月24日 下午5:25:42 nbin Exp $
 */
@Slf4j
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RedisServerTask extends Thread {
    
    private boolean  isRun     = false;
    
    private int      workSpace = 200;
    
    @Resource
    private BaseBufferPull bufferPull;

    @Override
    public void run() {
        isRun = true;
        while (isRun) {
            try {
                List<String> bufData = bufferPull.pullBuffer();
                if (bufData.size() == 2) {
                    int innum = bufferPull.writeData(bufData.get(1));
                    if (innum > -1) {
                        Jedis jedis = RedisUtil.getJedis();
                        if (jedis != null) {
                            try {
                                RedisUtil.hdel(BaseBufferPush.QueueTempKeyName, bufData.get(0));
                            } catch (Exception e) {
                                log.error("删除数据所在临时区域失败：" + e.getMessage(), e);
                            }
                        }
                        RedisUtil.returnJedis(jedis);
                    } else {
                        log.error("获取缓冲区数据并入库时失败：" + bufData);
                    }
                }
            } catch (Exception e) {
                log.error("获取缓冲区数据并入库时失败：" + e.getMessage(), e);
            }

            try {
                Thread.sleep(workSpace);
            } catch (InterruptedException e) {
                log.warn("InDBServerTask工作休眠时异常：", e.getMessage(), e);
            }

            Thread.yield();
        }
    }

    @Override
    public void start() {
        isRun = true;
        super.start();
    }

    /**
     * 停止当前线程循环任务<br>
     * 正在执行的任务会执行完成
     */
    public void StopMe() {
        isRun = false;
    }
}
