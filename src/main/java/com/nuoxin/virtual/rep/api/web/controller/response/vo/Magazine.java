package com.nuoxin.virtual.rep.api.web.controller.response.vo;

import java.io.Serializable;

public class Magazine implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id; // required
	private String name; // required
	private String issn; // required
	private String cn; // required
	private String type; // required
	private double compositeImpactFactor; // required
	private double syntheticalImpactFactor; // required

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getCompositeImpactFactor() {
		return compositeImpactFactor;
	}

	public void setCompositeImpactFactor(double compositeImpactFactor) {
		this.compositeImpactFactor = compositeImpactFactor;
	}

	public double getSyntheticalImpactFactor() {
		return syntheticalImpactFactor;
	}

	public void setSyntheticalImpactFactor(double syntheticalImpactFactor) {
		this.syntheticalImpactFactor = syntheticalImpactFactor;
	}
}
