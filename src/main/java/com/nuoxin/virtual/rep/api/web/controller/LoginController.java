package com.nuoxin.virtual.rep.api.web.controller;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.DrugUserService;
import com.nuoxin.virtual.rep.api.service.EmailService;
import com.nuoxin.virtual.rep.api.service.LoginService;
import com.nuoxin.virtual.rep.api.service.SercurityService;
import com.nuoxin.virtual.rep.api.web.controller.request.LoginRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.LoginResponseBean;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
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
    @Autowired
    private SercurityService sercurityService;
    @Autowired
    private DrugUserService drugUserService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/login")
    @ResponseBody
    public DefaultResponseBean<LoginResponseBean> login(@RequestBody LoginRequestBean bean,
                                     HttpServletRequest request, HttpServletResponse response){
        System.out.println(bean.getPassword());
        DrugUser drugUser = loginService.login(bean);
        sercurityService.saveSession(request,response,drugUser);
        LoginResponseBean result = new LoginResponseBean();
        result.setName(drugUser.getName());
        result.setEmail(drugUser.getEmail());
        DefaultResponseBean<LoginResponseBean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(result);
        return responseBean;
    }

    @GetMapping("/logout")
    @ResponseBody
    public DefaultResponseBean<Object> logout(HttpServletRequest request, HttpServletResponse response){
        sercurityService.cleanSession(request);
        DefaultResponseBean<Object> responseBean = new DefaultResponseBean<>();
        return responseBean;
    }

    @GetMapping("/retrieve/pwd/send")
    @ResponseBody
    public DefaultResponseBean<Object> emailCodeSend(@RequestParam("email") String email,
                                                     HttpServletRequest request, HttpServletResponse response) throws MessagingException {
        DrugUser drugUser = drugUserService.findByEmail(email);
        DefaultResponseBean<Object> responseBean = new DefaultResponseBean<>();
        if(drugUser==null){

        }
        emailService.sendEmailCode(drugUser);
        return responseBean;
    }


}
