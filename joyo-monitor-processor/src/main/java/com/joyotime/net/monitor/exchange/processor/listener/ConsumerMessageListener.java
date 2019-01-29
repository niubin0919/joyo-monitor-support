/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.processor.listener;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joyotime.net.monitor.exchange.processor.engine.core.PlatformDispatcher;

/**
 * 监听
 * @author nbin
 * @version $Id: ConsumerMessageListener.java, v 0.1 2019年1月28日 下午8:07:20 nbin Exp $
 */
public class ConsumerMessageListener implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerMessageListener.class);

    @Resource
    private PlatformDispatcher platformDispatcher;
    
    /** 
     * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
     */
    @Override
    public void onMessage(Message message) {
        try {
            ActiveMQDestination queues = (ActiveMQDestination) message.getJMSDestination();
            if (message instanceof TextMessage) {
                TextMessage tm = (TextMessage) message;
                try {
                    platformDispatcher.exeHandler(queues.getPhysicalName(), tm.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                    logger.info("queue listener error !", e);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("queue listener error !", e);
                }
            }
        } catch (Exception e) {
            logger.info("queue listener error !", e);
        }
    }
}
