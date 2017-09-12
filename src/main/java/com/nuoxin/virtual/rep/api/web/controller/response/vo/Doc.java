package com.nuoxin.virtual.rep.api.web.controller.response.vo;

import java.io.Serializable;

public class Doc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id; // required
	private long magazineId; // required
	private String name; // required
	private String magazineNo; // required
	private int referenceNum; // required
	private String authors; // required
	private String description;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMagazineId() {
		return magazineId;
	}
	public void setMagazineId(long magazineId) {
		this.magazineId = magazineId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMagazineNo() {
		return magazineNo;
	}
	public void setMagazineNo(String magazineNo) {
		this.magazineNo = magazineNo;
	}
	public int getReferenceNum() {
		return referenceNum;
	}
	public void setReferenceNum(int referenceNum) {
		this.referenceNum = referenceNum;
	}
	public String getAuthors() {
		return authors;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
