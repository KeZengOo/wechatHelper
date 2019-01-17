package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.enums.ShareStatusEnum;
import com.nuoxin.virtual.rep.api.mybatis.ActivityShareMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.ShareService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.share.ShareRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.share.ShareStatusRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.ContentCommentResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.ContentShareResponseBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tiancun
 * @date 2018-09-20
 */
@Service
public class ShareServiceImpl implements ShareService{

    @Resource
    private ActivityShareMapper activityShareMapper;

    @Override
    public PageResponseBean<ContentShareResponseBean> getContentShareList(DrugUser user,  ShareRequestBean bean) {
        bean.setLeaderPath(user.getLeaderPath() +"%");
        Integer page = bean.getPage();
        Integer pageSize = bean.getPageSize();
        bean.setCurrentSize(page  * pageSize);

        Integer contentShareListCount = activityShareMapper.getContentShareListCount(bean);
        if (contentShareListCount == null){
            contentShareListCount = 0;
        }
        List<ContentShareResponseBean> contentShareList = new ArrayList<>();
        if (contentShareListCount != null && contentShareListCount > 0){
            contentShareList = activityShareMapper.getContentShareList(bean);
            if (CollectionsUtil.isNotEmptyList(contentShareList)){
                for (ContentShareResponseBean contentShareResponseBean:contentShareList){
                    List<ContentCommentResponseBean> contentCommentList = activityShareMapper.getContentCommentList(bean.getDoctorId(), contentShareResponseBean.getContentId());
                    if (CollectionsUtil.isNotEmptyList(contentCommentList)){
                        contentShareResponseBean.setCommentList(contentCommentList);
                    }
                }
            }

        }

        PageResponseBean<ContentShareResponseBean> pageResponseBean = new PageResponseBean<>(bean, contentShareListCount, contentShareList);
        return pageResponseBean;
    }

    @Override
    public void saveOrUpdateShareStatus(ShareStatusRequestBean bean) {
        Long shareId = bean.getShareId();
        Integer shareStatus = bean.getShareStatus();
        if (shareId == null || shareStatus == null){
            throw new BusinessException(ErrorEnum.ERROR, "分享ID或者分享状态不能为空");
        }

        String status = ShareStatusEnum.getStatusByType(shareStatus);
        if (StringUtils.isEmpty(status)){
            throw new BusinessException(ErrorEnum.ERROR, "分享状态不合法");
        }

        Integer shareStatusCount = activityShareMapper.getShareStatusCount(shareId);
        if (shareStatusCount == null || shareStatusCount == 0){
            activityShareMapper.addShareStatus(shareId, shareStatus);
        }else{
            activityShareMapper.updateShareStatus(shareId, shareStatus);
        }

    }
}
