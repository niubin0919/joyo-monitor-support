/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.processor;

import org.springframework.beans.BeansException;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.joyotime.net.monitor.exchange.dao.ram.PlatformFtpRam;

import lombok.extern.slf4j.Slf4j;

/**
 * processor start
 * @author nbin
 * @version $Id: App.java, v 0.1 2019年1月24日 下午5:24:33 nbin Exp $
 */
@Slf4j
public class App {
    
    private static GenericXmlApplicationContext context;
    
    public static void main(String[] args) throws BeansException, Exception {
        log.info("-------->>processor server start.......");
        context = new GenericXmlApplicationContext();
        context.load("classpath:config/spring-context.xml");
        context.refresh();
        //初始mysql ftp配置到redis
        context.getBean(PlatformFtpRam.class).init();
        log.info("-------->>processor statistic success.......");
    }
}
