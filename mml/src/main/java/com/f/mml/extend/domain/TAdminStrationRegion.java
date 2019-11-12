package com.f.mml.extend.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_admin_stration_region")
@Excel("区域信息表")
public class TAdminStrationRegion implements Serializable {

    private static final long serialVersionUID = -7790334862410409056L;

    @ApiModelProperty(value = "编码")
    private String regionCode;

    private String parentRegionCode;

    private String regionName;

    private String regionLevel;

    private String remarks;


}