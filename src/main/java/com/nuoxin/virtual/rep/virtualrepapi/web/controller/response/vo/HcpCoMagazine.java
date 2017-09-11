package com.nuoxin.virtual.rep.virtualrepapi.web.controller.response.vo;

import java.io.Serializable;

public class HcpCoMagazine implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long magazineId; // required
	private String magazineName; // required
	private int coNum; // required
	public long getMagazineId() {
		return magazineId;
	}
	public void setMagazineId(long magazineId) {
		this.magazineId = magazineId;
	}
	public String getMagazineName() {
		return magazineName;
	}
	public void setMagazineName(String magazineName) {
		this.magazineName = magazineName;
	}
	public int getCoNum() {
		return coNum;
	}
	public void setCoNum(int coNum) {
		this.coNum = coNum;
	}
}
