package com.nuoxin.virtual.rep.api.web.controller;

import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fenggang on 10/11/17.
 */
@Api(value = "", description = "邮件接口")
@RestController
@RequestMapping(value = "/email")
public class EmailController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());
}
