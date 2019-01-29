/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.processor.engine.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.joyotime.net.monitor.exchange.dao.base.BaseBufferPush;




/**
 * 平台抽象engine，每个平台业务继承该类，实现一些通用方法<p>
 * @author nbin
 * @version $Id: PlatformAbatractEngine.java, v 0.1 2019年1月8日 下午3:16:39 nbin Exp $
 */
public abstract class  PlatformAbatractEngine implements PlatformBaseEngine {
    
    private List<BaseBufferPush>    bufferGroup = null;
    
    String message;
    
    protected abstract void regBufferGroup();
    /**
     * 注册 buffer，主要是一次上传的文件需要转换多种格式缓冲在redis
     * sql的缓冲单独处理不需要注册组
     * @param bufferPush
     */
    public void addBufferGroup(BaseBufferPush bufferPush){
        if (this.bufferGroup == null) {
            bufferGroup = new ArrayList<>();
        }
        bufferGroup.add(bufferPush);
    }
    /**
     * 推送到redis
     */
    private void pushBufferToRedis(){
        if (CollectionUtils.isEmpty(this.bufferGroup)) {
            return;
        }
        this.bufferGroup.forEach( e -> e.valuesBody(message));
    }
    
    @Override
    public void loadAttr(String message) {
        this.message = message;
    }
    
    /** 
     * @see com.joyotime.net.monitor.exchange.handler.engine.core.PlatformBaseEngine#exeHandler(java.lang.String, java.lang.String)
     */
    @Override
    public void exeHandler() {
        regBufferGroup();
        pushBufferToRedis();
    }

    
}
