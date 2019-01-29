/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.mq.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.joyotime.net.monitor.exchange.mq.config.ActivemqConfig;
import com.joyotime.net.monitor.exchange.mq.config.ActivemqUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * send mq
 * @author nbin
 * @version $Id: SendDataBase.java, v 0.1 2019年1月23日 下午7:22:21 nbin Exp $
 */
@Slf4j
public abstract class SendDataBase<T> implements SendData {
    
    protected String            platformName     = null;//处理平台名称
    protected String            queueName        = null;//队列名
    protected ActivemqUtil      activemq;
    private String              clientId         = null;
    private String              mqUri            = null;
    private String              mqUid            = null;
    private String              mqPwd            = null;
    private int                 buffSize         = 1;
    private Thread              connThread;
    private List<String>        tempQueue        = new Vector<String>();
    private List<String>        buffQueue        = new ArrayList<String>();
    private Thread              sendThread;
    private volatile boolean    isSender         = false;
    //pool
    private static final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    /**
     * init
     */
    protected void init() {
        try {
            clientId = ActivemqConfig.getClientID() + "_";
            mqUri = ActivemqConfig.getMqURI();
            mqUid = ActivemqConfig.getUid();
            mqPwd = ActivemqConfig.getPwd();
            buffSize = ActivemqConfig.getLocalBuffSize();
        } catch (Exception e) {
            log.error("SendDataBase-init error：" + e.getMessage(), e);
        }
        // 连接不阻塞
        connThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    log.info(platformName +"MQ开始连接.....");
                    activemq = new ActivemqUtil(clientId, mqUri, mqUid, mqPwd, queueName);
                    log.info(platformName +"MQ连接成功.....");
                } catch (Exception e) {
                    log.error(platformName + "MQ连接失败....." + e.getMessage(), e);
                }
            }
        });
        connThread.start();
        // 发送工作线程
        sendThread = new Thread(new Runnable() {
            @Override
            public void run() {
                sendWork();
            }
        });
        sendThread.start();
    }
    
    private void sendWork() {
        isSender = true;
        try {
            while (isSender) {
                try {
                    Thread.sleep(2);
                } catch (Exception e) {
                }
                try {
                    boolean isBuffData = false;
                    String data = null;
                    String strData = null;
                    if (activemq != null) {
                        // 如果连接，先发送缓冲数据，再发送临时数据
                        data = getBuffData();
                        isBuffData = true;
                        if (data == null) {
                            data = getTempData();
                            isBuffData = false;
                        }
                        if (data == null) {
                            continue;
                        }
                        try {
                            strData = data;
                            activemq.sendMessage(strData);
                            if (isBuffData) {
                                buffQueue.remove(data);
                            }
                            log.info(queueName+":send--ok");
                        } catch (Exception e) {
                            log.error(platformName + "推送失败消息内容:" + strData);
                            log.error(platformName + "推送失败消息失败：" + e.getMessage(), e);
                        }
                    } else {
                        data = getTempData();
                        if (data != null) {
                            strData = data;
                            if (buffSize > buffQueue.size()) {
                                buffQueue.add(data);
                                log.error(platformName + "未连接MQ，数据暂时缓存，后续发送" + strData);
                            } else {
                                log.error(platformName + "未连接MQ，缓存已满，数据抛弃" + strData);
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error(platformName + "发送失败：" + e.getMessage(), e);
                }
                Thread.yield();
            }
        } catch (Exception e) {
            log.error(platformName + "发送数据线程错误：" + e.getMessage(), e);
        }
    }
    

    /** 获取临时数据 */
    private String getTempData() {
        if (tempQueue.size() > 0) {
            String data = tempQueue.get(0);
            tempQueue.remove(data);
            return data;
        }
        return null;
    }

    /** 获取缓冲数据 */
    private String getBuffData() {
        if (buffQueue.size() > 0) {
            String data = buffQueue.get(0);
            return data;
        }
        return null;
    }
    
    /** 
     * @see com.joyotime.net.monitor.exchange.mq.core.SendData#send(java.lang.Object)
     */
    @Override
    public boolean send(final String data) {
        if (data == null) {
            return false;
        }
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                String strData = "";
                try {
                    if (tempQueue.size() > buffSize * 2) {
                        try {
                            strData = data;
                        } catch (Exception e) {
                            log.error(platformName + "临时区已满，发送失败，数据抛弃：" + strData);
                            log.error(platformName + "临时区已满，发送失败，数据抛弃：" + e.getMessage(), e);
                        }
                       
                    } else {
                        tempQueue.add(data);
                    }
                } catch (Exception e) {
                    try {
                        strData = data;
                    } catch (Exception e1) {
                        log.error(platformName + "发送失败，数据抛弃:" + strData);
                        log.error(platformName + "发送失败，数据抛弃:" + e.getMessage(), e);
                    }
                }
            }
        });
        return true;
    }

    /** 
     * @see com.joyotime.net.monitor.exchange.mq.core.SendData#close()
     */
    @Override
    public void close() {
        if (connThread != null) {
            connThread.stop();
            connThread = null;
        }
        if (sendThread != null) {
            isSender = false;
            sendThread = null;
        }
        if (activemq != null) {
            activemq.shutdown();
        }
        buffQueue.clear();
    }

}
