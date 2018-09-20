package com.nuoxin.virtual.rep.api.web.controller.v2_5;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	@Resource
	private DynamicFieldMapper dynamicFieldMapper;
	//分页
	private int page=1;
	//列表
	private int list=2;

	@ApiOperation(value = "拜访记录列表")
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

	@ApiOperation(value = "拜访记录列表导出")
	@RequestMapping(value = "/visit/statisticsListExportExcel", method = { RequestMethod.POST })
	public void statisticsListExportExcel(HttpServletRequest request, HttpServletResponse response,
																							   @RequestBody StatisticsParams statisticsParams) {
		DrugUser user = this.getDrugUser(request);
		if (user == null) {
			return ;
		}
		if(statisticsParams.getProductId()==null){
			return ;
		}
		if(statisticsParams.getDrugUserIds()==null||statisticsParams.getDrugUserIds().isEmpty()){
			return ;
		}
		String fileName="医生拜访统计表 "+statisticsParams.getStartTime()+"-"+statisticsParams.getEndTime();
		statisticsParams.setType(list);
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
		Map<String, String> map=new LinkedHashMap<>();
		map.put("drugUserName","代表");
		map.put("visitTime","拜访时间");
		map.put("doctorId","医生ID");
		map.put("doctorName","医生姓名");
		map.put("hospital","医院");
		map.put("visitType","拜访方式");
		map.put("shareContent","分享内容");
		map.put("visitResult","拜访结果");
		map.put("attitude","医生态度");
		map.put("nextVisitTime","下次拜访时间");
		List<DynamicFieldResponse> list=new ArrayList<>();
		map.forEach((k,v)->{
			DynamicFieldResponse t= new DynamicFieldResponse();
			t.setLable(v);
			t.setProp(k);
			list.add(t);
		});
		DynamicFieldResponse t= new DynamicFieldResponse();
		t.setLable(productName);
		t.setProp("product");
		List<String> data=dynamicFieldMapper.getProductDynamicField(productId);
		List<DynamicFieldResponse> fieldlist=new ArrayList<>();
		for(int i=0;i<data.size();i++){
			DynamicFieldResponse xx= new DynamicFieldResponse();
			xx.setLable(data.get(i));
			xx.setProp("field"+(i+1));
			fieldlist.add(xx);
		}
		t.setChildren(fieldlist);
		list.add(t);
		DefaultResponseBean<List<DynamicFieldResponse>> responseBean = new DefaultResponseBean<>();
		responseBean.setData(list);
		return responseBean;
	}

}
