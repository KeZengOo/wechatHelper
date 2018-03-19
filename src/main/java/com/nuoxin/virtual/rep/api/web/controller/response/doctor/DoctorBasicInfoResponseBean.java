package com.nuoxin.virtual.rep.api.web.controller.response.doctor;

import com.nuoxin.virtual.rep.api.web.controller.response.hcp.HcpBasicInfoHistoryResponseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Create by tiancun on 2017/10/19
 */
@ApiModel(value = "医生基本信息返回数据")
public class DoctorBasicInfoResponseBean implements Serializable{
    private static final long serialVersionUID = 5527300879361408851L;

    @ApiModelProperty(value = "动态字段的值")
    private Long ddfvId;

    @ApiModelProperty(value = "字段id")
    private Long fieldId;

    @ApiModelProperty(value = "字段名称")
    private String field;

    @ApiModelProperty(value = "对应下拉框的值，只有下拉框类型字段才会有的值")
    private String fieldValue;

    @ApiModelProperty(value = "添加的字段的值")
    private String value;

    @ApiModelProperty(value = "字段类型")
    private Integer type;

    @ApiModelProperty(value = "分类，例如1是基本信息，2医生的处方信息")
    private Integer classification;

    @ApiModelProperty(value = "标识，1是普通字段，0是需要特殊处理的字段")
    private Integer flag;

    @ApiModelProperty(value = "字段修改历史")
    private List<HcpBasicInfoHistoryResponseBean> list;



    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getClassification() {
        return classification;
    }

    public void setClassification(Integer classification) {
        this.classification = classification;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }


    public Long getDdfvId() {
        return ddfvId;
    }

    public void setDdfvId(Long ddfvId) {
        this.ddfvId = ddfvId;
    }


    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }


    public List<HcpBasicInfoHistoryResponseBean> getList() {
        return list;
    }

    public void setList(List<HcpBasicInfoHistoryResponseBean> list) {
        this.list = list;
    }


    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
