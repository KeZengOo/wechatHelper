package com.naxions.www.wechathelper;

public class DataBean {

	public String id;
	public String desc;

	public boolean isCheck;  //该属性主要标志CheckBox是否选中

	public DataBean(String id, String desc) {
		this.id = id;
		this.desc = desc;
	}
}