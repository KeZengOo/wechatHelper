package com.nuoxin.virtual.rep.api.web.controller.response.v3_0;

import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.daily.VisitResultDoctorNumStatisticsResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.daily.VisitResultHospitalNumStatisticsResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 日报返回数据
 * @author tiancun
 * @date 2019-05-09
 */
@Data
@ApiModel(value = "日报返回数据")
public class DailyReportResponse implements Serializable {
    private static final long serialVersionUID = -6958934923081128467L;

    @ApiModelProperty(value = "招募医生数")
    private Integer recruitDoctorNum;

    @ApiModelProperty(value = "覆盖医生数")
    private Integer coverDoctorNum;

    @ApiModelProperty(value = "覆盖医院率")
    private String coverDoctorRate;

    @ApiModelProperty(value = "目标医生数")
    private Integer targetDoctor;

    @ApiModelProperty(value = "未招募医生数")
    private Integer noRecruitDoctorNum;

    @ApiModelProperty(value = "活跃覆盖医生")
    private Integer activeCoverDoctorNum;


    @ApiModelProperty(value = "多渠道覆盖医生数")
    private Integer mulChannelDoctorNum;

    @ApiModelProperty(value = "不同的拜访结果医生数统计")
    private List<VisitResultDoctorNumStatisticsResponse> visitResultDoctorNumList = new ArrayList<>();

    @ApiModelProperty(value = "医生招募率")
    private String recruitDoctorRate;


    @ApiModelProperty(value = "微信回复人数")
    private Integer wechatReplyDoctorNum;


    @ApiModelProperty(value = "微信回复人次")
    private Integer wechatReplyDoctorCount;


    @ApiModelProperty(value = "有微信的人数")
    private Integer hasWechatDoctorNum;


    @ApiModelProperty(value = "添加微信的人数")
    private Integer addWechatDoctorNum;

    @ApiModelProperty(value = "有需求的医生人数")
    private Integer hasDemandDoctorNum;


    @ApiModelProperty(value = "有AE的医生人数")
    private Integer hasAeDoctorNum;

    @ApiModelProperty(value = "退出项目的医生数")
    private Integer quitDoctorNum;


    @ApiModelProperty(value = "招募的医院数")
    private Integer recruitHospitalNum;

    @ApiModelProperty(value = "覆盖医院数")
    private Integer coverHospitalNum;

    @ApiModelProperty(value = "覆盖医院率")
    private String coverHospitalRate;


    @ApiModelProperty(value = "未招募医院")
    private Integer noRecruitHospitalNum;


    @ApiModelProperty(value = "医院招募率")
    private String recruitHospitalRate;

    @ApiModelProperty(value = "目标医院")
    private Integer targetHospital;

    @ApiModelProperty(value = "不同的拜访结果医院数统计")
    private List<VisitResultHospitalNumStatisticsResponse> visitResultHospitalNumList = new ArrayList<>();


}
