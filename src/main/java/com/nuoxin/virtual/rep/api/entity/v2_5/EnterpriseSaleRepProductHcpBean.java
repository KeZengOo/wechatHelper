package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 代表-医生-产品关联表
 * @author wujiang
 * @date 2019-03-28
 */
@ApiModel("代表-医生-产品关联表")
@Data
public class EnterpriseSaleRepProductHcpBean {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "代表编码")
    private String saleRepCode;
    @ApiModelProperty(value = "代表名")
    private String saleRepName;
    @ApiModelProperty(value = "医生编码")
    private String customerCode;
    @ApiModelProperty(value = "医生名")
    private String hcpName;
    @ApiModelProperty(value = "产品 ID")
    private Integer productId;
    @ApiModelProperty(value = "产品编码")
    private String productCode;
    @ApiModelProperty(value = "产品名")
    private String productName;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
