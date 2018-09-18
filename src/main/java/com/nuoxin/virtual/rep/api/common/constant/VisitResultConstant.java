package com.nuoxin.virtual.rep.api.common.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 电话拜访记录结果类型
 * @author lichengxin
 */
public class VisitResultConstant {

	public static final String RECRUITMENTSUCCESS = "招募成功";
	public static final String TRANSFERSUCCESS = "传递成功";
	public static final String TYPINGSUCCESS= "分型成功";
	public static final String SERVICEPHONE = "服务电话";
	public static final String DOCTORREFUSED = "医生拒绝";
	public static final String CALLLATER = "稍后致电";

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