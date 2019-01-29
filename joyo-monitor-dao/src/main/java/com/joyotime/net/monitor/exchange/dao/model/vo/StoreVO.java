package com.joyotime.net.monitor.exchange.dao.model.vo;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.joyotime.net.monitor.exchange.dao.enums.FieldDefinition;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 商铺
 * @author rollinkin
 * @date 2017-08-09
 */
@Setter
@Getter
@ToString
public class StoreVO {
    
    private long storeId;
    
    private long platformId;
    
    private long safeCompanyId;//信托公司

    private int status;

    private Map<Integer, String> values;

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getPlatformId() {
        return platformId;
    }

    public void setPlatformId(long platformId) {
        this.platformId = platformId;
    }

    public long getSafeCompanyId() {
        return safeCompanyId;
    }

    public void setSafeCompanyId(long safeCompanyId) {
        this.safeCompanyId = safeCompanyId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<Integer, String> getValues() {
        return values;
    }

    public void setValues(Map<Integer, String> values) {
        this.values = values;
    }

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

    public String getDataSource() {
        String dataSource = "default";
        if (CollectionUtils.isEmpty(values)) {
            return dataSource;
        }
        if (StringUtils.isEmpty(values.get(FieldDefinition.DATA_SOURCE.getCode()))){
            dataSource = values.get(FieldDefinition.DATA_SOURCE.getCode());
        }
        return dataSource;
    }

    public String getDataDestination() {
        String dataDest = "default";
        if (CollectionUtils.isEmpty(values)) {
            return dataDest;
        }
        if (StringUtils.isEmpty(values.get(FieldDefinition.DATA_DESTINATION.getCode()))){
            dataDest = values.get(FieldDefinition.DATA_DESTINATION.getCode());
        }
        return dataDest;
    }

    public String getSiteCode() {
        return getByFieldCode(FieldDefinition.SITE_CODE.getCode());
    }

    public String getLongitude() {
        return getByFieldCode(FieldDefinition.LONGITUDE.getCode());
    }

    public String getLatitude(){
        return getByFieldCode(FieldDefinition.LATITUDE.getCode());
    }

    public String getStoreName() {
        return getByFieldCode(FieldDefinition.STORE_NAME.getCode());
    }

    public String getStoreAddr() {
        return getByFieldCode(FieldDefinition.STORE_ADDR.getCode());
    }

    public String getStoreType(){
        return getByFieldCode(FieldDefinition.STORE_TYPE.getCode());
    }

    public String getBusinessType() {
        return getByFieldCode(FieldDefinition.BUSINESS_TYPE.getCode());
    }

    /**
     * 场所法人
     * @return
     */
    public String getStorePerson() {
        return getByFieldCode(FieldDefinition.STORE_PERSON.getCode());
    }

    public String getNetType() {
        return getByFieldCode(FieldDefinition.NET_TYPE.getCode());
    }

    public String getNetProvide() {
        return getByFieldCode(FieldDefinition.NET_PROVIDER.getCode());
    }

    /**
     * 法人证件类型
     * @return
     */
    public String getCredentialsType(){
        return getByFieldCode(FieldDefinition.CREDENTIALS_TYPE.getCode());
    }

    /**
     * 法人证件号码
     * @return
     */
    public String getCredentialsCode() {
        return getByFieldCode(FieldDefinition.CREDENTIALS_CODE.getCode());
    }

    /**
     * 联系方式
     * @return
     */
    public String getStorePersonMobile() {
        return getByFieldCode(FieldDefinition.PHONE.getCode());
    }

    /**
     * 派出所代码
     * @return
     */
    public String getPoliceCode(){
        return getByFieldCode(FieldDefinition.POLICE_STATION_CODE.getCode());
    }

    public String getOpenStart() {
        return getByFieldCode(FieldDefinition.OPEN_DOOR_TIME.getCode());
    }

    public String getOpenEnd() {
        return getByFieldCode(FieldDefinition.CLOSE_DOOR_TIME.getCode());
    }
}
