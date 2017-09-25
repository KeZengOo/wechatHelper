package com.nuoxin.virtual.rep.api.web.controller;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.common.util.mem.SessionMemUtils;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.DrugUserService;
import com.nuoxin.virtual.rep.api.service.EmailService;
import com.nuoxin.virtual.rep.api.service.LoginService;
import com.nuoxin.virtual.rep.api.service.SercurityService;
import com.nuoxin.virtual.rep.api.web.controller.request.LoginRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.UpdatePwdRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.LoginResponseBean;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

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
    @Autowired
    private SessionMemUtils memUtils;

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
            responseBean.setCode(300);
            responseBean.setMessage("账号不存在");
        }
        responseBean.setMessage("验证码发送成功");
        emailService.sendEmailCode(drugUser);
        return responseBean;
    }

    @PostMapping("/retrieve/pwd/save")
    @ResponseBody
    public DefaultResponseBean<Object> updatePawd(@RequestBody UpdatePwdRequestBean bean,
                                                  HttpServletRequest request, HttpServletResponse response) throws MessagingException {
        DefaultResponseBean<Object> responseBean = new DefaultResponseBean<>();
        Object code = memUtils.get(bean.getEmail());
        if(code==null || "".equals(code)){
            responseBean.setCode(300);
            responseBean.setMessage("验证码过期");
        }
        if(!code.toString().equals(bean.getCode())){
            responseBean.setCode(300);
            responseBean.setMessage("验证码错误");
        }
        //校验密码格式
        if(!Pattern.matches("^[0-9a-zA-Z]{6,20}", bean.getPwassword())){
            responseBean.setCode(300);
            responseBean.setMessage("请输入6-20位英文、数字组合");
        }
        drugUserService.updatePawd(bean);
        responseBean.setMessage("修改密码成功");
        return responseBean;
    }


}
