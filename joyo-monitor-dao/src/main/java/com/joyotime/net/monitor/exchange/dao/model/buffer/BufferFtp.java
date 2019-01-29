/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.dao.model.buffer;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ftp上传文件处理类
 * @author nbin
 * @version $Id: BufferFtp.java, v 0.1 2019年1月16日 下午6:20:39 nbin Exp $
 */
@Setter
@Getter
@ToString
public class BufferFtp implements Serializable{
    
    private static final long serialVersionUID = -147943220212610911L;
    
    /** 平台*/
    private Integer platform;
    /** 门店*/
    private Integer store;
    /** 设备*/
    private String sn;
    /** 上传内容*/
    private String content;
    
}
