/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.processor.engine.core;

/**
 * 平台调度基础engine接口，提供给调度器使用
 * @author nbin
 * @version $Id: PlatformBaseEngine.java, v 0.1 2019年1月8日 下午3:16:44 nbin Exp $
 */
public interface PlatformBaseEngine {
    
    /**
     * 不同平台对应业务
     */
    void exeHandler();
    /**
     * 
     * @param message
     */
    void loadAttr(String message);
}
