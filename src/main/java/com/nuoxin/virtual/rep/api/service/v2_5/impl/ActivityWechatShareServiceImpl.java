package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import com.nuoxin.virtual.rep.api.mybatis.ActivityInfoMapper;
import com.nuoxin.virtual.rep.api.mybatis.ActivityWechatShareMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.ActivityWechatShareService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.share.WechatShareContentRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.wechat.ActivityWechatShareRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.content.ContentResponseBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tiancun
 * @date 2019-01-02
 */
@Service
public class ActivityWechatShareServiceImpl implements ActivityWechatShareService {

    @Resource
    private ActivityWechatShareMapper activityWechatShareMapper;

    @Resource
    private ActivityInfoMapper activityInfoMapper;

    @Override
    public void batchInsert(WechatShareContentRequestBean bean) {

        activityWechatShareMapper.batchInsert(bean);


    }

    @Override
    public List<ContentResponseBean> getProductContentList() {
        List<ContentResponseBean> productContentList = activityInfoMapper.getProductContentList();

        return productContentList;
    }
}
