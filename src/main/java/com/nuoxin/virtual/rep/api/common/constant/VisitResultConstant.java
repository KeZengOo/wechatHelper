package com.nuoxin.virtual.rep.api.common.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 电话拜访记录结果类型
 * @author lichengxin
 */
public class VisitResultConstant {

	public static final String RECRUITMENTSUCCESS = "成功招募";
	public static final String TRANSFERSUCCESS = "成功传递";
	public static final String TYPINGSUCCESS= "医生分型";
	public static final String SERVICEPHONE = "服务电话";
	public static final String DOCTORREFUSED = "医生拒绝";
	public static final String CALLLATER = "稍后致电";
	public static final String NONCONTACT = "非联系人";

	/**
	 * 接触医生数参数
	 * @return
	 */
	public static List<String> getContactDoctorParm(){
		List<String> cs=new ArrayList<>();
		cs.add(RECRUITMENTSUCCESS);
		cs.add(TRANSFERSUCCESS);
		cs.add(TYPINGSUCCESS);
		cs.add(SERVICEPHONE);
		cs.add(DOCTORREFUSED);
		cs.add(CALLLATER);
		return cs;
	}

	/**
	 * 成功医生数参数
	 * @return
	 */
	public static List<String> getSuccessDoctorParm(){
		List<String> cs=new ArrayList<>();
		cs.add(RECRUITMENTSUCCESS);
		cs.add(TRANSFERSUCCESS);
		cs.add(TYPINGSUCCESS);
		cs.add(SERVICEPHONE);
		return cs;
	}

	/**
	 * 招募医生参数
	 * @return
	 */
	public static List<String> getRecruitmentParm(){
		List<String> cs=new ArrayList<>();
		cs.add(RECRUITMENTSUCCESS);
		return cs;
	}

	/**
	 *覆盖医生参数
	 * @return
	 */
	public static List<String> getCoverDoctorParm(){
		List<String> cs=new ArrayList<>();
		cs.add(TYPINGSUCCESS);
		cs.add(SERVICEPHONE);
		return cs;
	}

}