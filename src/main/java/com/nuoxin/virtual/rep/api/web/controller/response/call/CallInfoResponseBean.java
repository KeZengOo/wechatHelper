package com.nuoxin.virtual.rep.api.web.controller.response.call;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tiancun
 * @date 2018-07-20
 */
@Data
public class CallInfoResponseBean implements Serializable {
    private static final long serialVersionUID = 1898308280366105844L;

    private Long id;

    private String sinToken;

    private String infoJson;

    private String callUrl;


    private String unpressedCallUrl;

    private String unpressedCallUrlIn;

    private String unpressedCallUrlOut;


    private Integer callTime;

    private String statusName;


}
