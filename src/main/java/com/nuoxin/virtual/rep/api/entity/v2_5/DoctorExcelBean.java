package com.nuoxin.virtual.rep.api.entity.v2_5;

import com.nuoxin.virtual.rep.api.common.annotations.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * 医生批量导入模版
 */
@Data
public class DoctorExcelBean implements Serializable {
    private static final long serialVersionUID = -8101729441861723175L;
    @Excel(name = "医生ID",width=100)
    private Integer customerCode;
    @Excel(name = "姓名",width=100)
    private String doctorName;
    @Excel(name = "手机号",width=100)
    private String mobile;
    @Excel(name = "医院",width=100)
    private String hospitalName;
    @Excel(name = "销售代表姓名",width=500)
    private String drugUserName;
    @Excel(name = "销售代表邮箱",width=500)
    private String drugUserEmail;
    @Excel(name = "性别",width=100)
    private String sex;
    @Excel(name = "地址",width=500)
    private String address;
    @Excel(name = "科室",width=100)
    private String department;
    @Excel(name = "医院省份",width=200)
    private String province;
    @Excel(name = "医院城市",width=100)
    private String city;
}
