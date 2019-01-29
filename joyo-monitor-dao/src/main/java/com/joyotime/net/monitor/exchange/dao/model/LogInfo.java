package com.joyotime.net.monitor.exchange.dao.model;


import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * loginfo记录信息
 * @author nbin
 * @version $Id: LogInfo.java, v 0.1 2019年1月17日 上午11:25:17 nbin Exp $
 */
@Setter
@Getter
@ToString
public class LogInfo {
    
    /**  */
    private static final long serialVersionUID = 1L;
    
    /** 主键id*/
    private long id;
    /** 记录时间*/
    private Date createTime;
    /** 店铺id*/
    private long storeId;
    /** 设备编号*/
    private String sn;
    /** 平台id*/
    private long platformId;
    /** mac 地址*/
    private String mac;
    /** 类型*/
    private int type;
    /** 消息时间*/
    private Date messageTime;
    /** 消息内容*/
    private String messageContent;
    /** 上传内容*/
    private String uploadContent;
    /** 上传时间*/
    private Date uploadTime;
    /** 上传结果*/
    private int uploadResult;
    /** 上传失败消息*/
    private String uploadFailMsg;
    
}
