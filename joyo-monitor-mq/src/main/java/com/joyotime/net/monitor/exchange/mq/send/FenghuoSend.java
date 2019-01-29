/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.mq.send;

import com.joyotime.net.monitor.exchange.dao.base.BaseEngineEnum;
import com.joyotime.net.monitor.exchange.dao.model.send.Fenghuo;
import com.joyotime.net.monitor.exchange.mq.core.SendDataBase;

/**
 * 
 * @author nbin
 * @version $Id: FenghuoSend.java, v 0.1 2019年1月28日 下午5:56:43 nbin Exp $
 */
public class FenghuoSend extends SendDataBase<Fenghuo> {
    
    private volatile static FenghuoSend instance;
    
    public static FenghuoSend getInstance(){
        if(instance == null){
            synchronized (FenghuoSend.class){
                if(instance == null){
                    instance = new FenghuoSend();
                }
            }
        }
        return instance;
    }
    public FenghuoSend() {
        queueName = BaseEngineEnum.fenghuo.getQueueName();
        platformName = BaseEngineEnum.fenghuo.getName();
        init();
    }
}
