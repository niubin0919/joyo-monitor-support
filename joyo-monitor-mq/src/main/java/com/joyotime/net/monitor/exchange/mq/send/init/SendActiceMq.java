/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.mq.send.init;

import com.alibaba.fastjson.JSON;
import com.joyotime.net.monitor.exchange.dao.base.BaseEngineEnum;
import com.joyotime.net.monitor.exchange.dao.model.send.base.BaseSend;
import com.joyotime.net.monitor.exchange.mq.send.FenghuoSend;
import com.joyotime.net.monitor.exchange.mq.send.PaiboSend;
import com.joyotime.net.monitor.exchange.mq.send.YuncengSend;

import lombok.extern.slf4j.Slf4j;

/**
 * 将日志数据文件发送到mq
 * @author nbin
 * @version $Id: SendActiceMq.java, v 0.1 2019年1月23日 下午8:06:06 nbin Exp $
 */
@Slf4j
public class SendActiceMq {
    
    static FenghuoSend fenghuoSend;
    static PaiboSend paiboSend;
    static YuncengSend yuncengSend;
    
    /**
     * 初始各个平台消息体
     * 我们默认消息体的内容是一样的，但是我们想把它根据平台区分开
     */
    static {
        fenghuoSend = FenghuoSend.getInstance();
        paiboSend   = PaiboSend.getInstance();
        yuncengSend = YuncengSend.getInstance();
    }
    
    /**
     * 发送
     * 不同平台有不同的实例和工作线程
     * @param data
     * @param platform
     */
    public static void send(String sn, long platformId, long storeId, String content){
        BaseEngineEnum platform = BaseEngineEnum.getPlatformEngineEnum(platformId);
        if(BaseEngineEnum.checkEnum(platformId)){
            String sendData = null;
            BaseSend bs = null;
            try {
                bs = new BaseSend(sn, platformId, storeId, content);
                sendData = JSON.toJSONString(bs);
            } catch (Exception e) {
                log.error(bs.toString());
                log.error(e.getMessage(),e);
            }    
            if(platform.getQueueName().equals(BaseEngineEnum.fenghuo.getQueueName())){
                fenghuoSend.send(sendData);
            }
            if(platform.getQueueName().equals(BaseEngineEnum.paibo.getQueueName())){
                paiboSend.send(sendData);
            }
            if(platform.getQueueName().equals(BaseEngineEnum.yunceng.getQueueName())){
                yuncengSend.send(sendData);
            }
        }else{
            log.error("不存在该平台信息platformId:"+platformId);
        }
    }
}
