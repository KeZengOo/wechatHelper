package com.nuoxin.virtual.rep.api.web.controller.request.analysis;

import com.nuoxin.virtual.rep.api.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by fenggang on 10/12/17.
 */
@ApiModel
public class TargetAnalysisRequestBean implements Serializable{
    private static final long serialVersionUID = 8831698029573694558L;

    @ApiModelProperty(value = "产品id")
    private Long productId;
    @ApiModelProperty(value = "日期（格式为:yyyy-MM-dd）")
    private String date;
    @ApiModelProperty(value = "日期类型(1-day,2-week,3-month,4-quarter)")
    private Integer dateType;
    @ApiModelProperty(value = "坐席id")
    private Long drugUserId;
    @ApiModelProperty(value = "客户等级")
    private String customLevel;
    @ApiModelProperty(value = "区域")
    private String area;

    @ApiModelProperty(value = "不用传")
    private String leaderPath = "%";
    @ApiModelProperty(value = "不用传")
    private String startDate;
    @ApiModelProperty(value = "不用传")
    private String endDate;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getDrugUserId() {
        return drugUserId;
    }

    public void setDrugUserId(Long drugUserId) {
        this.drugUserId = drugUserId;
    }

    public String getCustomLevel() {
        return customLevel;
    }

    public void setCustomLevel(String customLevel) {
        this.customLevel = customLevel;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getDateType() {
        return dateType;
    }

    public void setDateType(Integer dateType) {
        this.dateType = dateType;
    }

    public String getLeaderPath() {
        return leaderPath;
    }

    public void setLeaderPath(String leaderPath) {
        this.leaderPath = leaderPath;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void checkDate(){
        if(dateType==1){
            setStartDate(getDate()+" 00:00:00");
            setEndDate(getDate()+" 23:00:00");
        }else if(dateType==2){
            String[] dates = getDate().split("-");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.valueOf(dates[0]),Integer.valueOf(dates[1])-1,Integer.valueOf(dates[2]),0,0,0);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            if(weekDay==1){
                setEndDate(getDate()+" 23:59:59");
                calendar.add(Calendar.DAY_OF_YEAR,-6);
                setStartDate(DateUtil.getDateTimeString(calendar.getTime()));
            }else{
                calendar.add(Calendar.DAY_OF_YEAR,2-weekDay);
                setStartDate(DateUtil.getDateTimeString(calendar.getTime()));
                calendar.add(Calendar.DAY_OF_YEAR,9-weekDay);
                setEndDate(DateUtil.getDateTimeString(calendar.getTime()));
            }
        }else if(dateType==3){
            String[] dates = getDate().split("-");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.valueOf(dates[0]),Integer.valueOf(dates[1])-1,1,0,0,0);
            setStartDate(DateUtil.getDateTimeString(calendar.getTime()));
            calendar.add(Calendar.MONTH,+1);
            setEndDate(DateUtil.getDateTimeString(calendar.getTime()));
        }else if(dateType==4){
            String[] dates = getDate().split("-");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.valueOf(dates[0]),((int) calendar.get(Calendar.MONTH) / 3) * 3,1,0,0,0);
            setStartDate(DateUtil.getDateTimeString(calendar.getTime()));
            calendar.set(Integer.valueOf(dates[0]),((int) calendar.get(Calendar.MONTH) / 3) * 3 + 2,1,0,0,0);
            calendar.add(Calendar.MONTH,+1);
            setEndDate(DateUtil.getDateTimeString(calendar.getTime()));
        }
    }
}
