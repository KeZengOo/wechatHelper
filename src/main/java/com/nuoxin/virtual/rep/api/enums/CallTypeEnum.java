package com.nuoxin.virtual.rep.api.enums;

/**
 * Created by fenggang on 9/14/17.
 */
public enum CallTypeEnum {

    CALL_TYPE_CALLOUT(1,"呼出"),
    CALL_TYPE_INCALL(2,"呼入");

    private int type;

    private String value;

    CallTypeEnum(int type, String value){
        this.type = type;
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
