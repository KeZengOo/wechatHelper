package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.wechat.ActivityWechatShareRequestBean;

import java.util.List;

/**
 * @author tiancun
 * @date 2019-01-02
 */
public interface ActivityWechatShareMapper {

    /**
     * 批量新增
     * @param list
     */
    void batchInsert(List<ActivityWechatShareRequestBean> list);

}
