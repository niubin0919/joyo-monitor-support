/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.mq.send;

import com.joyotime.net.monitor.exchange.dao.base.BaseEngineEnum;
import com.joyotime.net.monitor.exchange.dao.model.send.Yunceng;
import com.joyotime.net.monitor.exchange.mq.core.SendDataBase;

/**
 * 
 * @author nbin
 * @version $Id: YuncengSend.java, v 0.1 2019年1月28日 下午5:58:48 nbin Exp $
 */
public class YuncengSend extends SendDataBase<Yunceng> {
    
    private volatile static YuncengSend instance;
    
    public static YuncengSend getInstance(){
        if(instance == null){
            synchronized (YuncengSend.class){
                if(instance == null){
                    instance = new YuncengSend();
                }
            }
        }
        return instance;
    }
    public YuncengSend() {
        queueName = BaseEngineEnum.yunceng.getQueueName();
        platformName = BaseEngineEnum.yunceng.getName();
        init();
    }
}
