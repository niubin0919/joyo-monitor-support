package com.joyotime.net.monitor.exchange.dao.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 平台ftp服务器配置
 * </p>
 *
 * @author nbin123
 * @since 2019-01-28
 */
@Getter
@Setter
@ToString
@TableName("t_platform_ftp")
public class PlatformFtp extends Model<PlatformFtp> {

    private static final long serialVersionUID = 1L;
    
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 父编号
     */
    @TableField("platform_id")
    private Integer platformId;
    /**
     * 关联编号
     */
    @TableField("platform_ftp_host")
    private String platformFtpHost;
    /**
     * 名称
     */
    @TableField("platform_ftp_port")
    private String platformFtpPort;
    /**
     * 注释
     */
    @TableField("platform_ftp_user")
    private String platformFtpUser;
    /**
     * 类名
     */
    @TableField("platform_ftp_password")
    private String platformFtpPassword;

}
