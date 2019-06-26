package com.nuoxin.virtual.rep.api.web.controller.response.v2_5.single;

import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.DoctorBasicDynamicFieldValueResponseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 新增单个医生时，如果医生已经存在回显的数据
 * @author tiancun
 * @date 2019-02-20
 */
@Data
@ApiModel(value = "新增单个医生时，如果医生已经存在回显的数据")
public class DoctorAddResponseBean implements Serializable {
    private static final long serialVersionUID = 7372803710804738609L;

    @ApiModelProperty(value = "医生ID")
    private Long doctorId;

    @ApiModelProperty(value = "医生姓名")
    private String doctorName;

    @ApiModelProperty(value = "性别（0：男，1：女，2：未知）")
    private Integer sex;

    @ApiModelProperty(value = "医生多个手机号")
    private List<String> telephones;

    @ApiModelProperty(value = "医生微信号")
    private String wechat;

    @ApiModelProperty(value = "是否添加微信：1是,0否")
    private Integer addWechat;

    @ApiModelProperty(value = "医生邮箱")
    private String email;

    @ApiModelProperty(value = "医生地址")
    private String address;

    @ApiModelProperty(value = "医生科室")
    private String depart;

    @Deprecated
    @ApiModelProperty(value = "医生职称, 废弃使用新的")
    private String position;


    @ApiModelProperty(value = "新的医生职称")
    private String doctorTitle;


    @ApiModelProperty(value = "新的医生职务")
    private String doctorPosition;



    @ApiModelProperty(value = "医生基本信息动态字段填入的值")
    private List<DoctorBasicDynamicFieldValueResponseBean> basicDynamicList = new ArrayList<>();

    @ApiModelProperty(value = "医院名称")
    private String hospitalName;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "医院等级")
    private Integer hospitalLevel;

    @ApiModelProperty(value = "医院信息动态字段填入的值")
    private List<DoctorBasicDynamicFieldValueResponseBean> hospitalDynamicList = new ArrayList<>();

}
