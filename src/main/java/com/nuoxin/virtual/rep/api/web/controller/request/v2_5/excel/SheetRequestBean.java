package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.excel;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tiancun
 * @date 2018-12-29
 */
@Data
public class SheetRequestBean implements Serializable {
    private static final long serialVersionUID = 9027978769624487209L;


    private List<LinkedHashMap<String,Object>> dataList;

    private Map<String, String> titleMap;

    private String sheetName;


    public List<LinkedHashMap<String, Object>> getDataList() {
        return dataList;
    }

    public void setDataList(List<LinkedHashMap<String, Object>> dataList) {
        this.dataList = dataList;
    }

    public Map<String, String> getTitleMap() {
        return titleMap;
    }

    public void setTitleMap(Map<String, String> titleMap) {
        this.titleMap = titleMap;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
}
