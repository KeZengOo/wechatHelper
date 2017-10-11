package com.nuoxin.virtual.rep.api.enums;

public enum MagazineTypeEnum {

	HALF_MONTH("半月",15),
	DOUBLE_MONTH("双月",60),
	MONTH("月刊",30),
	TEN("旬刊",10),
	
	WEEK("周刊",7),
	SEASON("季刊",180),
	WEEK2("周二刊",3),
	
	YEAR("年刊",365),
	HALF_YEAR("半年",182);
	
	private String name;
	private int value;
	
	private MagazineTypeEnum(String name, int value){
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
