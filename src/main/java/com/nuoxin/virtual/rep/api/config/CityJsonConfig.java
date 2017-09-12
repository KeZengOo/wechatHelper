package com.nuoxin.virtual.rep.api.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by fenggang on 8/17/17.
 */
@Component
public class CityJsonConfig {

    private HashMap<String,String> mapJson = new HashMap<String,String>(){{
        put("北京","北京市");
        put("上海","上海市");
        put("广东","广东省");
        put("广西","广西壮族自治区");
        put("江苏","江苏省");
        put("浙江","浙江省");
        put("安徽","安徽省");
        put("江西","江西省");
        put("福建","福建省");
        put("山东","山东省");
        put("山西","山西省");
        put("甘肃","甘肃省");
        put("河北","河北省");
        put("黑龙江","黑龙江省");
        put("河南","河南省");
        put("天津","天津市");
        put("辽宁","辽宁省");
        put("吉林","吉林省");
        put("湖北","湖北省");
        put("内蒙古","内蒙古自治区");
        put("湖南","湖南省");
        put("云南","云南省");
        put("四川","四川省");
        put("重庆","重庆市");
        put("陕西","陕西省");
        put("新疆","新疆维吾尔自治区");
        put("青海","青海省");
        put("海南","海南省");
        put("贵州","贵州省");
        put("宁夏","宁夏回族自治区");
        put("西藏","西藏自治区");
    }};

    /**
     * 检查省份名称
     * @param province
     * @return
     */
    public String checkProvince(String province){
        String result = getMapJson().get(province);
        if(!StringUtils.isEmpty(result))
            return result;
        return province;
    }

    public HashMap<String, String> getMapJson() {
        return mapJson;
    }

    public void setMapJson(HashMap<String, String> mapJson) {
        this.mapJson = mapJson;
    }
}
