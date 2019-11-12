package com.f.mml.extend.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wuwenze.poi.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *    附件
 * </p>
 * @author cf
 * @since 2019-09-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "附件", description = "")
@Excel
public class TFileInfo extends Model<TFileInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "file_id", type = IdType.AUTO)
    private String fileId;
    @ApiModelProperty(value = "")
    private String parentId;

    @ApiModelProperty(value = "文件原名称")
    private String originalName;
    @ApiModelProperty(value = "文件名称")
    private String fileName;
    @ApiModelProperty(value = "文件类型")
    private String fileType;
    @ApiModelProperty(value = "文件大小")
    private String fileSize;
    @ApiModelProperty(value = "绝对路径")
    private String filePath;
    @ApiModelProperty(value = "路径")
    private String fileUrl;
    @ApiModelProperty(value = "相对对路径")
    private String relativePath;

    @ApiModelProperty(value = "状态0不正常1删除")
    private Integer delFlag;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "添加时间")
    private LocalDateTime addTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "添加时间")
    private LocalDateTime updateTime;
    @Override
    protected Serializable pkVal() {
        return this.fileId;
    }


}
