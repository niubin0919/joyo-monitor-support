/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.dao.model.buffer;

import lombok.Getter;
import lombok.Setter;

/**
 * sql缓冲处理类
 * @author nbin
 * @version $Id: BufferSql.java, v 0.1 2019年1月16日 下午5:06:55 nbin Exp $
 */

@Setter
@Getter
public class BufferSql {
    
    private String sql;

    public BufferSql(String sql) {
        this.sql = sql;
    }
}
