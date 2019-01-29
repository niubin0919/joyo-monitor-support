/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.mq.core;

/**
 * 发送数据
 * @author nbin
 * @version $Id: SendData.java, v 0.1 2019年1月23日 下午7:20:54 nbin Exp $
 */
public interface SendData {
    
    /**
     * 异步发送消息
     * @param data
     * @return
     */
    public boolean send(String data);
    /**
     * 停止发送并关闭连接
     */
    public void close();
}
