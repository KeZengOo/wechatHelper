package com.nuoxin.virtual.rep.api.web.controller.v2_5;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "客户跟进首页相关接口")
@RequestMapping(value = "/customer/followup/index")
@RestController
public class CustomerFollowUpController {
	
	@ApiOperation(value = "获取所有医药代表接口", notes = "获取所有医药代表接口")
	@RequestMapping(value = "/drug_users/get", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseEntity<DefaultResponseBean<Boolean>> getAllDrugUsers() {
		return null;
	}
	
	@ApiOperation(value = "获取所有产品接口", notes = "获取所有产品接口")
	@RequestMapping(value = "/product_lines/get", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseEntity<DefaultResponseBean<Boolean>> getAllProductLines() {
		return null;
	}
	
	@ApiOperation(value = "搜索接口", notes = "搜索接口")
	@RequestMapping(value = "/search/{search}", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseEntity<DefaultResponseBean<Boolean>> search(@PathVariable(value = "search") String search) {
		return null;
	}
	
	@ApiOperation(value = "更多筛选接口", notes = "更多筛选接口")
	@RequestMapping(value = "/search/more", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<DefaultResponseBean<Boolean>> searchMore(@RequestBody Object object) {
		return null;
	}
	
	@ApiOperation(value = "列表", notes = "列表")
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseEntity<DefaultResponseBean<Boolean>> list(@PathVariable(value = "search") String search) {
		return null;
	}

}
