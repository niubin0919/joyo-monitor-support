package com.joyotime.net.monitor.exchange.dao.model.vo;

import java.util.Date;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.joyotime.net.monitor.exchange.dao.enums.FieldDefinition;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 设备
 * @author rollinkin
 * @date 2017-08-10
 */
@Setter
@Getter
@ToString
public class DeviceVO {
    
    private long id;
    
    private String sn;

    private long storeId;

    private String mac;

    private String ssid;

    private int status;

    private String softVersion;

    private String hardVersion;

    private Date addTime;

    private Map<Integer, String> values;

    /**
     * 根据字段编码获取其对应的值
     * @param fieldCode
     * @return
     */
    public String getByFieldCode(int fieldCode) {
        if (CollectionUtils.isEmpty(values)) {
            return null;
        }
        return values.get(fieldCode);
    }

    public String getLongitude() {
        return getByFieldCode(FieldDefinition.LONGITUDE.getCode());
    }

    public String getLatitude() {
        return getByFieldCode(FieldDefinition.LATITUDE.getCode());
    }
   
}
