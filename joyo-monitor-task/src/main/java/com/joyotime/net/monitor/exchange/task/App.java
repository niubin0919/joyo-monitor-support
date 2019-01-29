/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.task;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.joyotime.net.monitor.exchange.task.manager.RedisServerTaskManager;

import lombok.extern.slf4j.Slf4j;

/**
 * start
 * @author nbin
 * @version $Id: App.java, v 0.1 2019年1月24日 下午5:24:33 nbin Exp $
 */
@Slf4j
public class App {
    
    private static GenericXmlApplicationContext context;
    
    public static void main(String[] args) {
        log.info("-------->>redis server start.......");
        context = new GenericXmlApplicationContext();
        context.load("classpath:config/spring-context.xml");
        context.refresh();
        context.getBean(RedisServerTaskManager.class).Init();
        log.info("-------->>redis statistic success.......");
    }
}
