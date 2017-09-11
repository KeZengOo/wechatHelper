package com.nuoxin.virtual.rep.virtualrepapi.service;

import com.nuoxin.virtual.rep.virtualrepapi.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.virtualrepapi.common.exception.BusinessException;
import com.nuoxin.virtual.rep.virtualrepapi.common.util.PasswordEncoder;
import com.nuoxin.virtual.rep.virtualrepapi.entity.DrugUser;
import com.nuoxin.virtual.rep.virtualrepapi.web.controller.request.LoginRequestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fenggang on 9/11/17.
 */
@Service
public class LoginService {

    @Autowired
    private DrugUserService drugUserService;

    public void login(LoginRequestBean bean){
        DrugUser drugUser = drugUserService.findByEmail(bean.getUserName());
        if(drugUser==null){
            throw new BusinessException(ErrorEnum.LOGIN_ERROR.getStatus(),"用户名不存在");
        }
        if (!PasswordEncoder.checkPassword(bean.getPassword(), drugUser.getPassword())) {
            throw new BusinessException(ErrorEnum.LOGIN_ERROR.getStatus(),"用户名密码不匹配");
        }

    }
}
