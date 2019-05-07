package com.nuoxin.virtual.rep.api.service.v2_5;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nuoxin.virtual.rep.api.entity.v2_5.HospitalProvinceBean;
import com.nuoxin.virtual.rep.api.web.controller.response.DrugUserResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DoctorImportErrorResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DoctorTelephoneResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 通用业务接口
 * @author xiekaiyu
 */
public interface CommonService {
	
	/**
	 * 根据 leaderPath 获取所有的下属(直接 & 间接)  virtualDrugUserIds
	 * @param leaderPath
	 * @return
	 */
	 List<Long> getSubordinateIds(String leaderPath);
	 
	 /**
	  * 根据 leaderPath 获取所有的下属信息
	  * @param leaderPath
	  * @return
	  */
	 List<DrugUserResponseBean> getSubordinates(String leaderPath);
	 
	 /**
	  * 根据 drugUserId 获取 leaderPath
	  * @param drugUserId
	  * @return
	  */
	 String getLeaderPathById(Long drugUserId); 
	 
	/**
	 * 上次拜访时间,不保留小数<br>
	 * 按分钟,小时,天显示 
	 * @param delta
	 * @return
	 */
	String alterLastVisitTimeContent(long delta);

	/**
	 * 下次拜访时间<br>
	 * T = 下次跟进时间-当前日期。<br>
	 * 当T>0是，显示“T天后”；<br>
	 * 当T<0时，显示“T天前”；<br>
	 * 当T=0时，显示“今天”<br>
	 * @param nextVisitTime
	 * @return
	 */
	String alterNextVisitTimeContent(Date nextVisitTime);




	/**
	 * 根据现有的医生模板导入医生
	 * @param file 导入的文件
	 * @return 导出遇到的错误
	 */
	Map<String, DoctorImportErrorResponse> doctorImport(MultipartFile file);


	/**
	 * 根据现有的医生模板导入医生
	 * @param file
	 * @return 导出遇到的错误
	 */
	Map<String, DoctorImportErrorResponse> drugUserDoctorTransfer(MultipartFile file);

	/**
	 * 根据医院名称得到医院ID,如果没有医院新增返回ID
	 * @param hospitalProvinceBean
	 * @return
	 */
	Long getHospitalId(HospitalProvinceBean hospitalProvinceBean);

	/**
	 * 过滤掉无效的手机号
	 * @param doctorTelephones
	 * @return
	 */
	List<String> filterInvalidTelephones(List<DoctorTelephoneResponse> doctorTelephones);

	/**
	 * 产品名称和ID对应关系
	 * @return
	 */
	Map<String, Long> getProductNameIdMap();

	/**
	 * 产品ID和名称对应关系
	 * @return
	 */
	Map<Long, String> getProductIdNameMap();

}
