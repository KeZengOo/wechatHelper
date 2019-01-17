package com.nuoxin.virtual.rep.api.web.controller.request.call;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 临时功能，拜访历史记录
 * @author tiancun
 * @date 2018-12-20
 */
@Data
@ApiModel(value = "拜访历史记录")
public class VisitHistoryRequestBean implements Serializable {
    private static final long serialVersionUID = 8573275252770429292L;

    private Long callId;

    private Long mendId;

    private Long doctorId;

    private Long drugUserId;

    private String mobile;

    private Integer visitChannel;

    private String visitTime;

    private String remark;

    private Long productId;

    private String sinToken;

    private Integer visitResult;

    private Integer attitude;

    private Integer haveDrug;

    private Integer recruit;

    private Integer haveAe;

    private Integer target;

}
