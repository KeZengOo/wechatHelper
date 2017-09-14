package com.nuoxin.virtual.rep.api.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by fenggang on 9/11/17.
 */
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected Long getLoginId(HttpServletRequest request){
        return 0l;
    }
}
