/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.dao.ram;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.joyotime.net.monitor.exchange.dao.model.PlatformFtp;
import com.joyotime.net.monitor.exchange.dao.mysql.mapper.PlatformFtpMapper;

/**
 * 初始化各平台ftp信息到redis
 * @author nbin
 * @version $Id: PlatformFtpRam.java, v 0.1 2019年1月29日 下午3:04:53 nbin Exp $
 */
@Component
public class PlatformFtpRam {
    
    public static Map<Integer, PlatformFtp> map = new HashMap<Integer, PlatformFtp>();
    
    @Resource
    private PlatformFtpMapper platformFtpMapper;
    
    public void init() throws Exception{
       List<PlatformFtp> list = platformFtpMapper.selectList(new EntityWrapper<PlatformFtp>());
       map = list.stream().collect(Collectors.toMap(PlatformFtp::getPlatformId, a -> a,(k1,k2)->k1));
    }
    
    /**
     * 获得平台对应ftp配置
     * @param platformId
     * @return
     */
    public static PlatformFtp getPlatformFtpConfig(int platformId){
        PlatformFtp value = map.get(platformId);
        if(null != value){
            return value;
        }
        return null;
    }
    
}
