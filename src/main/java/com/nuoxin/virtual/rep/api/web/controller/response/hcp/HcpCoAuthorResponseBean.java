package com.nuoxin.virtual.rep.api.web.controller.response.hcp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 医生合作作者实体类
 * @author tiancun
 *
 */
@ApiModel(value = "医生合作作者")
public class HcpCoAuthorResponseBean implements Serializable,Comparable<HcpCoAuthorResponseBean>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "医生合作作者")
	private String authorName;
	
	@ApiModelProperty(value = "医生合作作者论文篇数")
	private Integer coNum;

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public Integer getCoNum() {
		return coNum;
	}

	public void setCoNum(Integer coNum) {
		this.coNum = coNum;
	}

	@Override  
    public int compareTo(HcpCoAuthorResponseBean p) {
        if(p.coNum > this.coNum){  
            return 1;  
        }else if(p.coNum == this.coNum){  
            return 0;  
        }else{  
            return -1;  
        }  
    }     
	
	
	
}
