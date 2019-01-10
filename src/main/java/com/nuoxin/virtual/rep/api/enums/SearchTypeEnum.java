package com.nuoxin.virtual.rep.api.enums;

/**
 * 客户跟进，搜索类型
 * @author tiancun
 * @date 2019-01-10
 */
public enum SearchTypeEnum {

    SEARCH_NOE(1,"下次拜访时间为今天的"),
    SEARCH_TWO(2,"根据医生潜力、分型应今日拜访的"),
    SEARCH_THREE(3,"离上次拜访时间已经到XX天的"),
    SEARCH_FOUR(4,"转入后应在XX天内拜访的"),
    SEARCH_FIVE(5,"今日需要发送参会提醒的"),
    SEARCH_SIX(6,"今日需要会议回访的");

    private int userType;

    private String userTypeName;

    SearchTypeEnum(int userType, String userTypeName){
        this.userType = userType;
        this.userTypeName = userTypeName;
    }

    public int getUserType() {
        return userType;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

}
