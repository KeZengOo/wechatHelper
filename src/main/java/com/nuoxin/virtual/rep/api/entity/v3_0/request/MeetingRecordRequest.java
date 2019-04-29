package com.nuoxin.virtual.rep.api.entity.v3_0.request;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 会议记录查询Request
 * @author wujiang
 * @date 20190429
 */
@Data
@ApiModel("会议记录查询Request")
public class MeetingRecordRequest extends PageRequestBean {
    private Integer productId;
}
