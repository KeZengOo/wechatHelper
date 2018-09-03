package com.nuoxin.virtual.rep.api.web.controller.v2_5;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "产品设置相关接口")
@RequestMapping(value = "/product/conf")
@RestController
public class ProductConfController {
	
	@ApiOperation(value = "产品信息列表接口", notes = "产品信息列表接口")
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseEntity<DefaultResponseBean<Boolean>> list() {
		return null;
	}
	
	@ApiOperation(value = "添加产品信息接口", notes = "添加产品信息接口")
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseEntity<DefaultResponseBean<Boolean>> add() {
		return null;
	}
	
	@ApiOperation(value = "复制产品信息接口", notes = "复制产品信息接口")
	@RequestMapping(value = "/copy", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseEntity<DefaultResponseBean<Boolean>> copy() {
		return null;
	}
}
