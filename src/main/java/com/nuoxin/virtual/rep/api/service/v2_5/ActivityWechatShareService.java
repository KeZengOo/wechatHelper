package com.nuoxin.virtual.rep.api.service.v2_5;

import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.share.WechatShareContentRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.wechat.ActivityWechatShareRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.DrugUserResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.content.ContentResponseBean;

import java.util.List;

/**
 * 微信分享手动记录
 * @author tiancun
 * @date 2019-01-02
 */
public interface ActivityWechatShareService {

    /**
     * 批量新增
     * @param bean
     */
    void batchInsert(WechatShareContentRequestBean bean);

    /**
     * 得到所有的产品文章
     * @return
     */
    List<ContentResponseBean> getProductContentList(Long productId);


}
