/**
 * com.joyotime.net Inc.
 * Copyright (c) 2019-2019 All Rights Reserved.
 */
package com.joyotime.net.monitor.exchange.web.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.joyotime.net.monitor.exchange.dao.enums.ResponseField;
import com.joyotime.net.monitor.exchange.dao.model.vo.DeviceVO;
import com.joyotime.net.monitor.exchange.dao.model.vo.StoreVO;
import com.joyotime.net.monitor.exchange.dao.mysql.service.DeviceInfoService;
import com.joyotime.net.monitor.exchange.dao.mysql.service.StoreInfoService;
import com.joyotime.net.monitor.exchange.exception.JoyoException;
import com.joyotime.net.monitor.exchange.mq.send.init.SendActiceMq;
import com.joyotime.net.monitor.exchange.utils.DateUtils;

import lombok.extern.slf4j.Slf4j;


/**
 * 
 * 
 * @author nbin
 * @version $Id: NetMonitoreController.java, v 0.1 2019年1月17日 下午5:29:41 nbin Exp $
 */
@Slf4j
@Controller
public class NetMonitoreController {
    
    @Resource
    private DeviceInfoService deviceInfoService;
    @Resource
    private StoreInfoService storeInfoService;
    
    private String   localDir;
    
    @PostConstruct
    public void init() {
        File file = new File(localDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!localDir.endsWith("/")) {
            localDir += "/";
        }
    }
    
    /**
     * 接收http请求
     * 
     * @param file
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("receive")
    @ResponseBody
    public String receive(@RequestParam("netLog") MultipartFile file,HttpServletRequest httpServletRequest) {
        JSONObject result = new JSONObject();
        String sn = httpServletRequest.getParameter("sn");
        if (StringUtils.isBlank(sn)) {
            result.put(ResponseField.CODE.getField(), 40001);
            result.put(ResponseField.ERROR_MSG.getField(), "sn参数为空");
            return result.toJSONString();
        }
        if (file == null || file.getSize() == 0) {
            result.put(ResponseField.CODE.getField(), 40002);
            result.put(ResponseField.ERROR_MSG.getField(), "网监日志文件为空");
            return result.toJSONString();
        }
        //1 根据sn查询设备，需要查询到该设备
        DeviceVO deviceInfoVO = deviceInfoService.findByDeviceSn(sn);
        if (null == deviceInfoVO) {
            result.put(ResponseField.CODE.getField(), 40003);
            result.put(ResponseField.ERROR_MSG.getField(), "该SN:"+sn+" 不需要上传数据，没有设备信息！");
            return result.toJSONString();
        }
        //2 获取平台id
        StoreVO storeVO = storeInfoService.findByStoreId(deviceInfoVO.getStoreId());
        if (null == storeVO) {
            result.put(ResponseField.CODE.getField(), 40003);
            result.put(ResponseField.ERROR_MSG.getField(), "该SN:"+sn+" 不需要上传数据，没有商铺信息！");
            return result.toJSONString();
        }
        Long platformId = storeVO.getPlatformId();
        //3 文件转换  SN_接收时间_UUID_原始文件名
        String sourceFileName = file.getOriginalFilename();
        String dir = localDir + DateUtils.formatDate(new Date(), DateUtils.DATE_FORMAT_YYYYMMDD_HH);
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        String distFileName = dir + sn + "_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().replaceAll("-", "") + "_" + sourceFileName;
        
        /*
         * 缓冲文件，这个地方最好还是需要异步处理
         * 不用之前的每个请求都用单独的线程处理，而使用每个平台单独一个不阻塞线程，并加入线程池。
         * 不需要保留文件，保留文件不方便查看，如果这样的话没有mq缓冲会丢失数据，
         * 暂时考虑到的办法是把上传信息直接对应每个平台发送到mq队列缓冲，在由消费端处理相应平台的数据缓冲和业务处理
         * 最后使用调度线程更新缓冲后的sql和ftp上传
         */
        String data = "";
        BufferedReader reader = null;
        try {
            /*
             * 4 读取文件内容 
             * TODO文件的格式和内容的格式还不是很清楚
             */
            file.transferTo(new File(distFileName));
            reader = new BufferedReader(new FileReader(distFileName));
            while (StringUtils.isNotEmpty(data = reader.readLine())) {
                //5 发送mq
                SendActiceMq.send(sn, platformId, storeVO.getStoreId(), data);
            }
            //6 http端流程结束，返回处理状态
            result.put(ResponseField.CODE.getField(), 0);
        }catch (JoyoException e) {
            log.error(e.getMessage(),e);
            result.put(ResponseField.CODE.getField(), e.getCode());
            result.put(ResponseField.ERROR_MSG.getField(), e.getErrorMsg());
        }catch (Exception e) {
            result.put(ResponseField.CODE.getField(), 50000);
            result.put(ResponseField.ERROR_MSG.getField(), "系统内部错误");
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }
        }
        return result.toJSONString();
    }
}
