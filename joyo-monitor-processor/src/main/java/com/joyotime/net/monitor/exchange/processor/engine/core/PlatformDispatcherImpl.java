/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.processor.engine.core;

import java.util.Collection;
import java.util.Map;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.target.SingletonTargetSource;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * 平台业务调度器，业务统一入口
 * @author nbin
 * @version $Id: PlatformDispatcherImpl.java, v 0.1 2019年1月8日 下午3:16:54 nbin Exp $
 */
@Service
public class PlatformDispatcherImpl implements ApplicationContextAware, PlatformDispatcher {

    private ApplicationContext  context;
    
    /** 
     * @see com.joyotime.net.monitor.exchange.processor.core.PlatformDispatcher#exeHandler(java.lang.String, java.lang.String)
     */
    @Override
    public void exeHandler(String key, String message) {
        PlatformBaseEngine engine = getEngine(key);
        engine.loadAttr(message);
        engine.exeHandler();
    }
    
    private PlatformBaseEngine getEngine(String id) {
        Map<String, Object> engineMap = context.getBeansWithAnnotation(PlatformEngine.class);
        if (engineMap.isEmpty()) {
            throw new RuntimeException("no platform!");
        }
        Collection<Object> engines = engineMap.values();
        for (Object engine : engines) {
            if (engine instanceof Advised) {
                Advised advised = (Advised) engine;
                SingletonTargetSource singTarget = (SingletonTargetSource) advised.getTargetSource();
                engine = singTarget.getTarget();
            }
            PlatformEngine platform =  engine.getClass().getAnnotation(PlatformEngine.class);
            if (platform.value().getFlag() == Long.parseLong(id)) {
                return (PlatformBaseEngine) engine;
            }
        }
        throw new RuntimeException("no platform id:" + id);
    }
    /** 
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }


}
