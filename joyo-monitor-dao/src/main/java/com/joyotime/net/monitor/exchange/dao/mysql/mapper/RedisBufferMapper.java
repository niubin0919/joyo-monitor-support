/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.dao.mysql.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.joyotime.net.monitor.exchange.dao.model.buffer.BufferSql;

/**
 * 
 * @author nbin
 * @version $Id: RedisBufferMapper.java, v 0.1 2019年1月16日 下午5:06:04 nbin Exp $
 */
public interface RedisBufferMapper extends BaseMapper<BufferSql> {
    
    /**
     * @param sql
     * @return
     */
    int insertBuffer(BufferSql sql);
    
}
