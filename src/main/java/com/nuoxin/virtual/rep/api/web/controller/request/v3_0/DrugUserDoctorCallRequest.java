package com.nuoxin.virtual.rep.api.web.controller.request.v3_0;

import com.nuoxin.virtual.rep.api.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 代表医生拜访请求参数
 * @author tiancun
 * @date 2019-05-09
 */
@ApiModel(value = "代表医生拜访请求参数")
public class DrugUserDoctorCallRequest extends DoctorBaseRequest implements Serializable {
    private static final long serialVersionUID = -4577313097920015193L;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "是否是可分页的,0和null 可以分页的，其他是可以分页的")
    private Integer paginable;


    public Date getStartTime() {
        return startTime;
    }


    /**
     * 前端传的YYYY-MM-DD 需要转换
     * @param startTime
     */
    public void setStartTime(Date startTime) {
        if (startTime !=null){
            String dateString = DateUtil.getDateString(startTime);
            String dateTimeString = dateString.concat(" 00:00:00");
            Date date = DateUtil.stringToDate(dateTimeString, DateUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);

            this.startTime = date;
        }else {
            this.startTime = startTime;
        }

    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {



        if (endTime !=null){
            String dateString = DateUtil.getDateString(endTime);
            String dateTimeString = dateString.concat(" 00:00:00");
            Date date = DateUtil.stringToDate(dateTimeString, DateUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
            this.endTime = date;
        }else {
            this.endTime = endTime;
        }

    }

    public Integer getPaginable() {
        return paginable;
    }

    public void setPaginable(Integer paginable) {
        this.paginable = paginable;
    }
}
