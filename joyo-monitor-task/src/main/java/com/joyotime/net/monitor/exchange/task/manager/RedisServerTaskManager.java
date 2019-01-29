/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.task.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.joyotime.net.monitor.exchange.dao.base.BaseBufferPush;
import com.joyotime.net.monitor.exchange.dao.redis.util.RedisUtil;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * buffer任务管理器 <br>
 * 根据任务堆积的多少，动态调整任务处理的数量
 * @author nbin
 * @version $Id: RedisServerTaskManager.java, v 0.1 2019年1月24日 下午5:27:47 nbin Exp $
 */
@Slf4j
@Component
public class RedisServerTaskManager implements ApplicationContextAware {

    private Thread                managerTask          = null;

    private List<RedisServerTask> workTaskList         = null;
   
    private long                  oneTaskHandlerNumber = 1000;
    
    private int                   maxTaskNumber        = 50;

    private ApplicationContext    applicationContext;

    public RedisServerTaskManager() {
        workTaskList = new ArrayList<>();
        oneTaskHandlerNumber = 200;
        maxTaskNumber = 100;
    }
    
    public void Init() {
        managerTask = new Thread(() -> {
            while (true) {
                try {
                    // 如果还没有任务处理线程就创建一个线程
                    if (workTaskList.size() == 0) {
                        RedisServerTask redisServerTask = applicationContext.getBean(RedisServerTask.class);
                        workTaskList.add(redisServerTask);
                        redisServerTask.start();
                    } else {
                        // 获取当前任务队列长度
                        long listLength = -1;
                        Jedis jedis = RedisUtil.getJedis();
                        if (jedis != null) {
                            listLength = jedis.llen(BaseBufferPush.QueueKeyName);
                            RedisUtil.returnJedis(jedis);//需要回收资源！
                        }
                        if (listLength == -1) {
                            return;
                        }
                        // 根据任务长度计算当前应该有多少个工作线程
                        int mustWorkNumber = (int) (listLength / oneTaskHandlerNumber);
                        if ((listLength % oneTaskHandlerNumber) > (oneTaskHandlerNumber / 2)) {
                            mustWorkNumber += 1;
                        }
                        if (mustWorkNumber <= 0) {
                            mustWorkNumber = 1;
                        }
                        // 需要减少工作线程
                        if (mustWorkNumber > 1 && workTaskList.size() > mustWorkNumber) {
                            int reduce = workTaskList.size() - mustWorkNumber;
                            for (int i = 0; i < reduce; i++) {
                                RedisServerTask thread = workTaskList.remove(0);
                                thread.StopMe();
                            }
                        }
                        // 需要增加工作线程
                        else if (workTaskList.size() < mustWorkNumber) {
                            // 验证是否达到最大线程数
                            if (workTaskList.size() < maxTaskNumber) {
                                int increase = maxTaskNumber - workTaskList.size();
                                for (int i = 0; i < increase; i++) {
                                    RedisServerTask redisServerTask = applicationContext.getBean(RedisServerTask.class);
                                    workTaskList.add(redisServerTask);
                                    redisServerTask.start();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("RedisDateServer Manager error：" + e.getMessage(), e);
                }

                try {
                    Thread.sleep(1000 * 60);
                } catch (Exception e) {
                }
                Thread.yield();
            }

        });
        managerTask.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
