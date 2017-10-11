package com.nuoxin.virtual.rep.api.web.controller.response.hcp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 医生合作期刊
 * @author tiancun
 *
 */
@ApiModel(value = "医生合作专刊")
public class HcpCoMagazineResponseBean implements Serializable,Comparable<HcpCoMagazineResponseBean>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "专刊名称")
	private String magazineName;
	
	@ApiModelProperty(value = "合作专刊数量")
	private Integer coNum;

	public String getMagazineName() {
		return magazineName;
	}

	public void setMagazineName(String magazineName) {
		this.magazineName = magazineName;
	}

	public Integer getCoNum() {
		return coNum;
	}

	public void setCoNum(Integer coNum) {
		this.coNum = coNum;
	}

	@Override  
    public int compareTo(HcpCoMagazineResponseBean p) {
        if(p.coNum > this.coNum){  
            return 1;  
        }else if(p.coNum == this.coNum){  
            return 0;  
        }else{  
            return -1;  
        }  
    }     
	
	

}
