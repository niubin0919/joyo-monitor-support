/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.processor.engine.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.joyotime.net.monitor.exchange.dao.base.BaseEngineEnum;


/**
 * 平台标识，用于标识平台业务实现
 * @author nbin
 * @version $Id: PlatformEngine.java, v 0.1 2019年1月8日 下午3:17:00 nbin Exp $
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PlatformEngine {
    BaseEngineEnum value();
}
