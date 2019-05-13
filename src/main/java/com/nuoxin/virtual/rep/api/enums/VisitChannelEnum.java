package com.nuoxin.virtual.rep.api.enums;

/**
 * 拜访渠道枚举
 *
 * @author tiancun
 * @date 2019-05-13
 */
public enum VisitChannelEnum {

    TELEPHONE(1, "电话"),
    WECHAT(2, "微信"),
    MESSAGE(3, "短信"),
    EMAIL(4, "邮件"),
    INTERVIEW(5, "面谈"),
    CONTENT_SHARE(6, "内容分享"),
    OFFLINE_CALL(7, "线下电话");

    /**
     * 拜访渠道对应的值
     */
    private Integer visitChannel;

    /**
     * 拜访去掉对应的值
     */
    private String visitChannelStr;


    VisitChannelEnum(Integer visitChannel, String visitChannelStr) {
        this.visitChannel = visitChannel;
        this.visitChannelStr = visitChannelStr;
    }


    /**
     * 根据数值得到显示的文本
     * @param visitChannel
     * @return
     */
    public static String getVisitChannelStr(Integer visitChannel) {
        if (visitChannel == null) {
            return "未知";
        }

        String visitChannelStr = "";
        switch (visitChannel) {

            case 1:
                visitChannelStr = "电话";
                break;
            case 2:
                visitChannelStr = "微信";
                break;
            case 3:
                visitChannelStr = "短信";
                break;
            case 4:
                visitChannelStr = "邮件";
                break;
            case 5:
                visitChannelStr = "面谈";
                break;
            case 6:
                visitChannelStr = "内容分享";
                break;
            case 7:
                visitChannelStr = "线下电话";
                break;
            default:
                visitChannelStr = "未知";

        }

        return visitChannelStr;

    }


    public Integer getVisitChannel() {
        return visitChannel;
    }

    public void setVisitChannel(Integer visitChannel) {
        this.visitChannel = visitChannel;
    }

    public String getVisitChannelStr() {
        return visitChannelStr;
    }

    public void setVisitChannelStr(String visitChannelStr) {
        this.visitChannelStr = visitChannelStr;
    }
}
