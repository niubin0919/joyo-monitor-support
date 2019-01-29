/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.processor.engine;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.joyotime.net.monitor.exchange.dao.base.BaseEngineEnum;
import com.joyotime.net.monitor.exchange.processor.engine.core.PlatformAbatractEngine;
import com.joyotime.net.monitor.exchange.processor.engine.core.PlatformEngine;

/**
 * 上海云辰 相关文件分片缓存实现引擎
 * @author nbin
 * @version $Id: YuncengEngine.java, v 0.1 2019年1月28日 下午7:53:01 nbin Exp $
 */
@PlatformEngine(value = BaseEngineEnum.yunceng)
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class YuncengEngine extends PlatformAbatractEngine {

    /** 
     * 注册缓冲脚本组
     * @see com.joyotime.net.monitor.exchange.processor.engine.core.PlatformAbatractEngine#regBufferGroup()
     */
    @Override
    protected void regBufferGroup() {
        //上传ftp脚本缓冲
        //生成loginfo日志缓冲
    }
    
    
}
