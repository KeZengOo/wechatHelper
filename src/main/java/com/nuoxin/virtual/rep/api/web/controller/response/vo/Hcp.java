package com.nuoxin.virtual.rep.api.web.controller.response.vo;

import java.io.Serializable;
import java.util.Date;

public class Hcp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private long hciId;
	
	private String name;
	
	private String mobile;
	
	private String email;
	
	private String identityNo;
	
	private String description;
	
	private Date createTime;
	
	private Date updateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getHciId() {
		return hciId;
	}

	public void setHciId(long hciId) {
		this.hciId = hciId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdentityNo() {
		return identityNo;
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
