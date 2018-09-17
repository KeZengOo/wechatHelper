package com.nuoxin.virtual.rep.api.common.bean;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.TableHeader;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class PageResponseBean<T> implements Serializable {

	private static final long serialVersionUID = 6887389993060457824L;

	/**
	 * 返回数据内容
	 */
	@ApiModelProperty(value = "返回数据内容")
	private List<T> content;
	/**
	 * 总条目数
	 */
	@ApiModelProperty(value = "总条目数")
	private long totalElements;
	/**
	 * 总页数
	 */
	@ApiModelProperty(value = " 总页数")
	private int totalPages;
	/**
	 * 是否是最后一页
	 */
	@ApiModelProperty(value = "是否是最后一页")
	private boolean last;
	/**
	 * 当前页
	 */
	@ApiModelProperty(value = "当前页")
	private int number = 1;
	/**
	 * 当前页的条目数
	 */
	@ApiModelProperty(value = "当前页的条目数")
	private int size = 20;
	private int numberOfElements;
	/**
	 * 是否是第一页
	 */
	@ApiModelProperty(value = "是否是第一页")
	private boolean first;

	public PageResponseBean() {
		super();
	}

	public PageResponseBean(PageRequestBean bean, Integer count, List<T> list) {
		super();
		this.totalElements = count;
		if (count == 0) {
			this.totalPages = 0;
			this.first = false;
			this.last = false;
			this.size = 0;
			this.number = 0;
			this.numberOfElements = 0;
		} else {
			if (bean.getPageSize() == 0) {
				throw new BusinessException(ErrorEnum.PAGE_ERROR);
			}
			// this.totalPages = (int)Math.ceil(count/bean.getPageSize());
			int mod = count % (bean.getPageSize());
			if (mod == 0) {
				this.totalPages = count / bean.getPageSize();
			} else if (mod > 0) {
				this.totalPages = count / bean.getPageSize() + 1;
			} else {
				this.totalPages = 0;
			}

			this.number = bean.getPage() + 1;
			this.size = bean.getPageSize();
			if (list == null) {
				this.numberOfElements = 0;
			} else {
				this.numberOfElements = list.size();
			}

			if (bean.getPage() == (totalPages - 1)) {
				this.last = true;
			} else {
				this.last = false;
			}
			if (bean.getPage() == 0) {
				this.first = true;
			} else {
				this.first = false;
			}
		}
		this.content = list;
	}

	public PageResponseBean(Page<?> page) {
		super();
		this.totalElements = page.getTotalElements();
		this.totalPages = page.getTotalPages();
		this.first = page.isFirst();
		this.last = page.isLast();
		this.size = page.getSize();
		this.number = page.getNumber() + 1;
		this.numberOfElements = page.getNumberOfElements();
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public int getTotalPages() {

		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

}
