package com.nuoxin.virtual.rep.api.web.controller.request.vo;

import com.nuoxin.virtual.rep.api.common.annotations.Excel;
import lombok.Data;

/**
 * 代表对应的医生转移
 * @author tiancun
 * @date 2019-05-07
 */
@Data
public class DrugUserDoctorTransferVo {

    @Excel(name = "当前代表邮箱", width = 50)
    private String drugUserEmail;

    @Excel(name = "产品名称")
    private String productName;

    @Excel(name = "医生ID")
    private String doctorIdStr;

    @Excel(name = "转给代表的邮箱", width = 50)
    private String toDrugUserEmail;
}
