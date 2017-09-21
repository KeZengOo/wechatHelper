package com.nuoxin.virtual.rep.api.enums;

/**
 * 医院级别 11一级甲等，12一级乙等，13一级丙等，14一级特等，21二级甲等，22二级乙等，23二级丙等，31三级甲等，32三级乙等，33三级丙等
 * Create by tiancun on 2017/9/21
 */
public enum HospitalLevelEnum {


    FIRST_CLASS_A(11, "一级甲等"),
    FIRST_CLASS_B(12, "一级乙等"),
    FIRST_CLASS_C(13, "一级丙等"),
    FIRST_CLASS_D(14, "一级特等"),
    SECOND_CLASS_A(21, "二级甲等"),
    SECOND_CLASS_B(22, "二级乙等"),
    SECOND_CLASS_C(23, "二级丙等"),
    THIRD_CLASS_A(31, "三级甲等"),
    THIRD_CLASS_B(32, "三级乙等"),
    THIRD_CLASS_C(33, "三级丙等");


    private int value;

    private String name;


    HospitalLevelEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }


    public static String getName(int value) {
        String name = "";
        switch (value) {

            case 11:
                name = "一级甲等";
                break;
            case 12:
                name = "一级乙等";
                break;
            case 13:
                name = "一级丙等";
                break;
            case 14:
                name = "一级特等";
                break;
            case 21:
                name = "二级甲等";
                break;
            case 22:
                name = "二级乙等";
                break;
            case 23:
                name = "二级丙等";
                break;
            case 31:
                name = "三级甲等";
                break;
            case 32:
                name = "三级乙等";
                break;
            case 33:
                name = "三级丙等";
                break;
            default:
                name = "未知";

        }

        return name;

    }


    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
