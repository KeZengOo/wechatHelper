package com.nuoxin.virtual.rep.api.web.controller.response.vo;

import java.io.Serializable;
import java.util.List;


public class HcpResearchInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long hcpId; // required
	private int totalDocNum; // required
	private int totalReferenceNum; // required
	private List<Doc> docList; // required
	private List<HcpCoAuthor> coAuthorList; // required
	private List<HcpCoMagazine> coMagazineList; // required
	public long getHcpId() {
		return hcpId;
	}
	public void setHcpId(long hcpId) {
		this.hcpId = hcpId;
	}
	public int getTotalDocNum() {
		return totalDocNum;
	}
	public void setTotalDocNum(int totalDocNum) {
		this.totalDocNum = totalDocNum;
	}
	public int getTotalReferenceNum() {
		return totalReferenceNum;
	}
	public void setTotalReferenceNum(int totalReferenceNum) {
		this.totalReferenceNum = totalReferenceNum;
	}
	public List<Doc> getDocList() {
		return docList;
	}
	public void setDocList(List<Doc> docList) {
		this.docList = docList;
	}
	public List<HcpCoAuthor> getCoAuthorList() {
		return coAuthorList;
	}
	public void setCoAuthorList(List<HcpCoAuthor> coAuthorList) {
		this.coAuthorList = coAuthorList;
	}
	public List<HcpCoMagazine> getCoMagazineList() {
		return coMagazineList;
	}
	public void setCoMagazineList(List<HcpCoMagazine> coMagazineList) {
		this.coMagazineList = coMagazineList;
	}

}
