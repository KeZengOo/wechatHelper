package com.nuoxin.virtual.rep.api.common.controller;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.config.SessionConfig;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by fenggang on 9/11/17.
 */
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected Long getLoginId(HttpServletRequest request){
        DrugUser du = (DrugUser) request.getAttribute(SessionConfig.DEFAULT_REQUEST_DRUG_USER);
        if(du!=null){
            return du.getId();
        }
        return 0l;
    }
    protected DrugUser getLoginUser(HttpServletRequest request){
        DrugUser du = (DrugUser) request.getAttribute(SessionConfig.DEFAULT_REQUEST_DRUG_USER);
        if(du!=null){
            return du;
        }else{
            throw new BusinessException(ErrorEnum.LOGIN_NO);
        }
    }
}
