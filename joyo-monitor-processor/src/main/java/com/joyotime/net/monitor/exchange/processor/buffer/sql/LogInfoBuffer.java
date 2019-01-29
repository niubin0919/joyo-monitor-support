/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.processor.buffer.sql;

import java.util.Date;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.joyotime.net.monitor.exchange.dao.base.BaseBufferPush;
import com.joyotime.net.monitor.exchange.dao.model.LogInfo;
import com.joyotime.net.monitor.exchange.utils.DateUtils;

/**
 * logInfo日志信息buffer
 * @author nbin
 * @version $Id: LogInfoBuffer.java, v 0.1 2019年1月24日 下午7:08:41 nbin Exp $
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LogInfoBuffer extends BaseBufferPush {
    
    public LogInfoBuffer(){
        setBufferName("LogInfoBuffer");
        StringBuffer sb = new StringBuffer();
        sb.append("insert into log_info(");
        sb.append("create_time,");
        sb.append("store_id,");
        sb.append("sn,");
        sb.append("platform_id,");
        sb.append("mac,");
        sb.append("type,");
        sb.append("message_time,");
        sb.append("message_content,");
        sb.append("upload_content,");
        sb.append("upload_time,");
        sb.append("upload_result,");
        sb.append("upload_fail_obj");
        sb.append(") values ");
        setPrefix(sb.toString());//前缀
        setBufferSize(100);//缓冲条数  后面可以做成配置
    }
    
    /** 
     * @see com.joyotime.net.monitor.exchange.dao.base.BaseBufferPush#appendBody(com.joyotime.net.monitor.exchange.dao.base.BaseModel)
     */
    @Override
    public String appendBody(String msg) {
        if (null == msg) {
            return null;
        }
        
        return  null;
    }
    
    private String valuesBodyFromLogInfo(LogInfo obj) {
        if (obj == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        sb.append("(");
        //create_time
        sb.append("'").append(DateUtils.formatDate(new Date(), DateUtils.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS)).append("',");
        //store_id
        sb.append(obj.getStoreId()).append(",");
        //sn
        sb.append("'").append(obj.getSn()).append("',");
        //platform_id
        sb.append(obj.getPlatformId()).append(",");
        //mac
        sb.append("'").append(obj.getMac()).append("',");
        //message_time
        sb.append(DateUtils.formatDate(obj.getMessageTime(), DateUtils.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS)).append(",");
        //message_content
        sb.append("'").append(obj.getMessageContent()).append("',");
        //upload_content
        sb.append("'").append(obj.getUploadContent()).append("',");
        //upload_time
        sb.append(DateUtils.formatDate(obj.getUploadTime(), DateUtils.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS)).append(",");
        //upload_result
        sb.append(obj.getUploadResult()).append(",");
        //upload_fail_obj
        sb.append("'").append(obj.getUploadFailMsg()).append("'");
        sb.append(")");
        return sb.toString();
    }

}
