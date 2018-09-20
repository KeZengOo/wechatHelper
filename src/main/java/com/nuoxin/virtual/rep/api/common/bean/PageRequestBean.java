package com.nuoxin.virtual.rep.api.common.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel
public class PageRequestBean implements Serializable {

	private static final long serialVersionUID = -5460916727522078000L;

	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_SIZE = 10;

	/**
	 * 当前页，默认0
	 */
	@ApiModelProperty(value="当前页，默认1")
	private int page = DEFAULT_PAGE;

	/**
	 * 每页多少条，默认10条
	 */
	@ApiModelProperty(value="每页多少条，默认10条")
	private int pageSize = DEFAULT_SIZE;

	@ApiModelProperty(value="前端不用传")
	private Integer currentSize;

	public int getPage() {
		return (page-1)>0?(page-1):0;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		if(pageSize == 0) {
			pageSize = 10;
		}
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentSize() {
		if(page < 1) {
			page = 1;
		}
		
		if(pageSize == 0) {
			pageSize=10;
		}
		this.currentSize = (this.page - 1) * this.pageSize;
		return currentSize;
	}

	public void setCurrentSize(Integer currentSize) {
		this.currentSize = currentSize;
	}

	@Override
	public String toString() {
		return ""+ page + pageSize;
	}
}
