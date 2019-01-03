package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.share.WechatShareContentRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.wechat.ActivityWechatShareRequestBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tiancun
 * @date 2019-01-02
 */
public interface ActivityWechatShareMapper {

    /**
     * 批量新增
     * @param bean
     */
    void batchInsert(@Param(value = "bean") WechatShareContentRequestBean bean);

}
