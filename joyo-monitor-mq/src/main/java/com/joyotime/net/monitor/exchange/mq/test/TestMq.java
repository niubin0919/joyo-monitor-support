/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.mq.test;

import java.util.Random;
import java.util.UUID;

import com.joyotime.net.monitor.exchange.mq.send.init.SendActiceMq;

/**
 * 
 * @author nbin
 * @version $Id: TestMq.java, v 0.1 2019年1月29日 下午4:11:31 nbin Exp $
 */
public class TestMq {
    
    public static Integer[] platformIds = {1,4,5};
    
    public static Integer[][] storeIds = {{1,101},{1,102},{1,103},{4,401},{4,402},{4,103},{5,501},{5,502},{5,503}};
    
    public static void main(String[] args) {
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                Long count = 0L;
                while(true){
                    count++;
                    System.out.println("发送Mq"+ count);
                    try {
                        Integer[] store = storeIds[new Random().nextInt(storeIds.length)];
                        String sn = UUID.randomUUID().toString().replaceAll("-", "");
                        SendActiceMq.send(sn, store[0], store[1], sn);
                        Thread.sleep(10000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Thread.yield();
                }
            }
        }).start();
    }
}
