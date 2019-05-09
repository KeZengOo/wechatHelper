package com.nuoxin.virtual.rep.api.entity.v3_0.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 文件返回内容
 * @author wujiang
 * @date 20190508
 */
@Data
@ApiModel("文件返回内容")
public class FilePathParams implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 标签详情
     */
    @ApiModelProperty(value = "服务器地址")
    private String outPutPath;

    /**
     * 标签详情
     */
    @ApiModelProperty(value = "文件名称")
    private String fileName;

}
