package com.nuoxin.virtual.rep.virtualrepapi.web.controller.response.vo;

import java.io.Serializable;

public class DocKeyWord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int count;
	
	private String keyword;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	
}
