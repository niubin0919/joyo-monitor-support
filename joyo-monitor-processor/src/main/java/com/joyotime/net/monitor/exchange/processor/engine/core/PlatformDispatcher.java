/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.processor.engine.core;

/**
 * 
 * 平台业务调度器，业务统一入口
 * @author nbin
 * @version $Id: PlatformDispatcher.java, v 0.1 2019年1月8日 下午3:16:49 nbin Exp $
 */
public interface PlatformDispatcher {
    
    
    /**
     * 执行相关buffer
     * 后续业务操作
     * @param key
     * @param message
     */
    void exeHandler(String key, String message);
}
