/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.mq.send;

import com.joyotime.net.monitor.exchange.dao.base.BaseEngineEnum;
import com.joyotime.net.monitor.exchange.dao.model.send.Paibo;
import com.joyotime.net.monitor.exchange.mq.core.SendDataBase;

/**
 * 
 * @author nbin
 * @version $Id: PaiboSend.java, v 0.1 2019年1月28日 下午5:57:50 nbin Exp $
 */
public class PaiboSend extends SendDataBase<Paibo> {
    
    private volatile static PaiboSend instance;
    
    public static PaiboSend getInstance(){
        if(instance == null){
            synchronized (PaiboSend.class){
                if(instance == null){
                    instance = new PaiboSend();
                }
            }
        }
        return instance;
    }
    public PaiboSend() {
        queueName = BaseEngineEnum.paibo.getQueueName();
        platformName = BaseEngineEnum.paibo.getName();
        init();
    }
}
