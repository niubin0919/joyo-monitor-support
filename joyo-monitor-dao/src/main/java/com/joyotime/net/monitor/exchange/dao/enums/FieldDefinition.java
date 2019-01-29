package com.joyotime.net.monitor.exchange.dao.enums;

/**
 * 字段定义
 * @author rollinkin
 * @date 2017-08-08
 */
public enum FieldDefinition {
    LONGITUDE(1001,"经度"),
    LATITUDE(1002,"纬度"),
    STORE_NAME(1003,"门店名称	"),
    DATA_SOURCE(1004,"数据来源"),
    DATA_DESTINATION(1005,"数据目的地	"),
    STORE_ADDR(1006,"门店地址"),
    SITE_CODE(1007,"上网场所编码"),
    STORE_TYPE(1008,"场所类型"),
    BUSINESS_TYPE(1009,"场所经营性质"),
    STORE_PERSON(1010,"场所经营法人"),
    CREDENTIALS_TYPE(1011,"经营法人证件类型"),
    CREDENTIALS_CODE(1012,"经营法人证件号码"),
    PHONE(1013,"联系方式"),
    OPEN_DOOR_TIME(1014,"营业开始时间"),
    CLOSE_DOOR_TIME(1015,"营业结束时间"),
    NET_TYPE(1016,"场所网络接入方式"),
    NET_PROVIDER(1017,"场所网络接入服务商"),
    NET_ACCOUNT_OR_IP(1018,"网络接入账号或固定IP地址"),
    PARENT_STORE_NAME(1019,"父场所名称"),
    SUB_STORE_TYPE(1020,"场所子类型"),
    STORE_ORG_CODE(1021,"场所组织机构代码"),
    NET_SAFE_DEPT_NAME(1022,"所属网安部门名称"),
    NET_SAFE_DEPT_CODE(1023,"所属网安部门代码"),
    POLICE_STATION_NAME(1024,"所在派出所名称"),
    POLICE_STATION_CODE(1025,"所在派出所代码"),
    SAFE_PERSON(1026,"安全责任人姓名"),
    SAFE_PHONE(1027,"安全责任人电话"),
    SAFE_POSITION(1028,"安全责任人职务"),
    NET_ORG(1029,"场所网络接入单位"),
    NET_START_IP(1030,"接入网络开始IP"),
    NET_END_IP(1031,"接入网络结束IP"),
    NET_HAS_DEVICE(1032,"接入网络是否有设备"),
    STORE_SERVICE_TYPE(1033, "场所服务类型"),

    ;

    private int code;

    private String desc;

    FieldDefinition(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
