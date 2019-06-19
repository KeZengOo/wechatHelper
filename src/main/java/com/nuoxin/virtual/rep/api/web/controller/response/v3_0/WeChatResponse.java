package com.nuoxin.virtual.rep.api.web.controller.response.v3_0;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName WeChatResponse
 * @Description 微信覆盖
 * @Author dangjunhui
 * @Date 2019/6/19 10:38
 * @Version 1.0
 */
@Data
public class WeChatResponse implements Serializable {

    private String timeStr;

    private Integer sendNum;

    private Integer replyNum;

    private Integer hcpNum;

}
