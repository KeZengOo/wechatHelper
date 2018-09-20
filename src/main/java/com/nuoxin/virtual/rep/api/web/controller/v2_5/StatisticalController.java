package com.nuoxin.virtual.rep.api.web.controller.v2_5;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.util.StringUtils;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.v2_5.DynamicFieldResponse;
import com.nuoxin.virtual.rep.api.entity.v2_5.HospitalProvinceBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsResponse;
import com.nuoxin.virtual.rep.api.mybatis.DrugUserDoctorMapper;
import com.nuoxin.virtual.rep.api.mybatis.DynamicFieldMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.StatisticalService;
import com.nuoxin.virtual.rep.api.service.v2_5.VirtualDoctorService;
import com.nuoxin.virtual.rep.api.utils.ExportExcel;
import com.nuoxin.virtual.rep.api.utils.ExportExcelTitle;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.SaveVirtualDoctorRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

/**
 * 医生 Controller 类
 * @author xiekaiyu
 */
@Api(value = "V2.5统计分析")
@RequestMapping(value = "/statistics")
@Controller
public class StatisticalController extends NewBaseController {
	
	@Resource
	private StatisticalService statisticalService;
	@Resource
	private DrugUserDoctorMapper drugUserDoctorMapper;
	//分页
	private int page=1;
	//列表
	private int list=2;

	@ApiOperation(value = "医生拜访统计列表")
	@ResponseBody
	@RequestMapping(value = "/visit/statisticsList", method = { RequestMethod.POST })
	public DefaultResponseBean<PageResponseBean<List<StatisticsResponse>>> visitStatisticsList(HttpServletRequest request,
			@RequestBody StatisticsParams statisticsParams) {
		DrugUser user = this.getDrugUser(request);
		if (user == null) {
			return super.getLoginErrorResponse();
		}
		if(statisticsParams.getProductId()==null){
			return super.getParamsErrorResponse("ProductId is null");
		}
		if(statisticsParams.getDrugUserIds()==null||statisticsParams.getDrugUserIds().isEmpty()){
			return super.getParamsErrorResponse("DrugUserIds is null");
		}
		statisticsParams.setType(page);
		PageResponseBean<List<StatisticsResponse>> page=statisticalService.visitStatisticsPage(statisticsParams);
		DefaultResponseBean<PageResponseBean<List<StatisticsResponse>>> responseBean = new DefaultResponseBean<>();
		responseBean.setData(page);
		return responseBean;
	}

	@ApiOperation(value = "医生拜访统计表导出")
	@RequestMapping(value = "/visit/statisticsListExportExcel", method = { RequestMethod.GET })
	public void statisticsListExportExcel( HttpServletRequest request, HttpServletResponse response,
										  Integer productId,String drugUserIds,String startTime,String endTime) {
		/*DrugUser user = this.getDrugUser(request);*/
		/*if (user == null) {
			return ;
		}*/
		if(productId==null){
			return ;
		}
		if(drugUserIds==null|| drugUserIds==""){
			return ;
		}
		List<Integer> ids=new ArrayList<>();
		for(String str : drugUserIds.split(",")) {
			int i = Integer.valueOf(str);
			ids.add(i);
		}
		StatisticsParams statisticsParams=new StatisticsParams();
		statisticsParams.setEndTime(endTime);
		statisticsParams.setStartTime(startTime);
		statisticsParams.setType(list);
		statisticsParams.setDrugUserIds(ids);
		statisticsParams.setProductId(productId);
		String fileName="医生拜访统计表 "+statisticsParams.getStartTime()+"-"+statisticsParams.getEndTime()+".xls";

		List<StatisticsResponse> list=statisticalService.visitStatisticsList(statisticsParams);
		HSSFWorkbook wb=ExportExcel.excelExport(list, ExportExcelTitle.getStatisticsListTitleMap(),"医生拜访统计表");
		OutputStream ouputStream = null;
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
			response.setHeader("Pragma", "No-cache");
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@ApiOperation(value = "医生拜访明细列表")
	@ResponseBody
	@RequestMapping(value = "/visit/doctorVisitDetailList", method = { RequestMethod.POST })
	public DefaultResponseBean<PageResponseBean<List<LinkedHashMap<String,Object>>>> doctorVisitDetailList(HttpServletRequest request,
																							   @RequestBody StatisticsParams statisticsParams) {
		DrugUser user = this.getDrugUser(request);
		if (user == null) {
			return super.getLoginErrorResponse();
		}
		if(statisticsParams.getProductId()==null){
			return super.getParamsErrorResponse("ProductId is null");
		}
		statisticsParams.setType(page);
		PageResponseBean<List<LinkedHashMap<String,Object>>> page=statisticalService.doctorVisitDetailPage(statisticsParams);
		DefaultResponseBean<PageResponseBean<List<LinkedHashMap<String,Object>>>> responseBean = new DefaultResponseBean<>();
		responseBean.setData(page);
		return responseBean;
	}


	@ApiOperation(value = "根据产品id查询销售列表")
	@ResponseBody
	@RequestMapping(value = "/visit/getDrugUser/{productId}", method = { RequestMethod.GET })
	public DefaultResponseBean<List<StatisticsResponse>> getDrugUserIdByProductId(HttpServletRequest request,
																							   @PathVariable(value = "productId") Integer productId) {
		DrugUser user = this.getDrugUser(request);
		if (user == null) {
			return super.getLoginErrorResponse();
		}
		if(productId==null){
			return super.getParamsErrorResponse("ProductId is null");
		}
		List<StatisticsResponse> data=drugUserDoctorMapper.getDrugUserIdByProductId(productId,user.getLeaderPath());
		DefaultResponseBean<List<StatisticsResponse>> responseBean = new DefaultResponseBean<>();
		responseBean.setData(data);
		return responseBean;
	}

	@ApiOperation(value = "根据产品id查询动态字段")
	@ResponseBody
	@RequestMapping(value = "/visit/getDynamicFieldByProductId/{productId}/{productName}", method = { RequestMethod.GET })
	public DefaultResponseBean<List<DynamicFieldResponse>> getDynamicFieldByProductId(HttpServletRequest request,
				@PathVariable(value = "productId") Integer productId,@PathVariable(value = "productName") String productName) {
		DrugUser user = this.getDrugUser(request);
		if (user == null) {
			return super.getLoginErrorResponse();
		}
		if(productId==null){
			return super.getParamsErrorResponse("ProductId is null");
		}
		List<DynamicFieldResponse> list=statisticalService.getDynamicFieldByProductId(productId,productName);
		DefaultResponseBean<List<DynamicFieldResponse>> responseBean = new DefaultResponseBean<>();
		responseBean.setData(list);
		return responseBean;
	}

}
