package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.content.ContentResponseBean;

import java.util.List;

/**
 * @author tiancun
 * @date 2019-01-02
 */
public interface ActivityInfoMapper {

    /**
     * 得到所有的产品文章
     * @return
     */
    List<ContentResponseBean> getProductContentList();

}
