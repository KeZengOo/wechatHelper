package com.nuoxin.virtual.rep.virtualrepapi.web.controller.response.vo;

import java.io.Serializable;
import java.util.List;

public class DocKeyWordPage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int total;
	
	private int offset;
	
	private int size;
	
	private List<DocKeyWord> datas;
	
	private int totalPage;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<DocKeyWord> getDatas() {
		return datas;
	}

	public void setDatas(List<DocKeyWord> datas) {
		this.datas = datas;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
}
