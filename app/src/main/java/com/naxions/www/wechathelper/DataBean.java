package com.naxions.www.wechathelper;

/**
 * @Author: zengke
 * @Date: 2018.12
 *
 */
public class DataBean {

	public String id;
	public String desc;
	/**
	 * 该属性主要标志CheckBox是否选中
	 */
	public boolean isCheck;
	public DataBean(String id, String desc) {
		this.id = id;
		this.desc = desc;
	}
}