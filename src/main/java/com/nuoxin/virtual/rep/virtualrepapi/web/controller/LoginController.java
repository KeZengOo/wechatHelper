package com.nuoxin.virtual.rep.virtualrepapi.web.controller;

import com.nuoxin.virtual.rep.virtualrepapi.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.virtualrepapi.common.controller.BaseController;
import com.nuoxin.virtual.rep.virtualrepapi.entity.DrugUser;
import com.nuoxin.virtual.rep.virtualrepapi.service.DrugUserService;
import com.nuoxin.virtual.rep.virtualrepapi.service.LoginService;
import com.nuoxin.virtual.rep.virtualrepapi.web.controller.request.LoginRequestBean;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fenggang on 9/11/17.
 */
@Api(value = "",description = "")
@RestController
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    @ResponseBody
    public DefaultResponseBean<?> login(@RequestBody LoginRequestBean bean,
                                     HttpServletRequest request, HttpServletResponse response){
        System.out.println(bean.getPassword());
        loginService.login(bean);
        DefaultResponseBean<?> responseBean = new DefaultResponseBean<>();
        return responseBean;
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){

    }

}
