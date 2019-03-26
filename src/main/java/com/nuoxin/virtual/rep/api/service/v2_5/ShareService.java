package com.nuoxin.virtual.rep.api.service.v2_5;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.share.QuestionnaireRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.share.ShareRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.share.ShareStatusRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.ContentShareResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.wechat.ContentQuestionnaireAnswerResponseBean;

import java.util.List;

/**
 * 分享相关业务接口
 * @author tiancun
 * @date 2018-09-20
 */
public interface ShareService {

    /**
     * 内容分享记录
     * @param bean
     * @return
     */
    PageResponseBean<ContentShareResponseBean> getContentShareList(DrugUser user, ShareRequestBean bean);


    /**
     * 得到内容问卷答案
     * @param bean
     * @return
     */
    List<ContentQuestionnaireAnswerResponseBean> getContentQuestionnaireAnswer(QuestionnaireRequestBean bean);

    /**
     * 保存或者更新分享类型
     * @param bean
     */
    void saveOrUpdateShareStatus(ShareStatusRequestBean bean);

}
