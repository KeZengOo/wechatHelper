package com.nuoxin.virtual.rep.api.web.controller.request.vo;

import com.nuoxin.virtual.rep.api.common.annotations.Excel;
import lombok.Data;

/**
 * 医生导入模板实体类
 * @author tiancun
 * @date 2019-04-29
 */
@Data
public class DoctorVo {


    @Excel(name = "医院所在省")
    private String province;
    @Excel(name = "医院所在市")

    private String city;

    @Excel(name = "医院名称")
    private String hospitalName;


    @Excel(name = "医院地址", width = 200)
    private String address;

    @Excel(name = "医院级别")
    private String hospitalLevel;

    @Excel(name = "药企提供的医院ID")
    private String drugHospitalId;

    @Excel(name = "医生姓名")
    private String doctorName;

    @Excel(name = "医生性别")
    private String sex;

    @Excel(name = "医生手机号")
    private String telephone;

    @Excel(name = "科室名称")
    private String depart;

    @Excel(name = "职称")
    private String positions;

    @Excel(name = "代表工作邮箱", width = 50)
    private String drugUserEmail;


}
