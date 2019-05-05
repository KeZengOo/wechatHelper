package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.ContentSharingParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.ContentSharingRequest;
import com.nuoxin.virtual.rep.api.mybatis.ContentSharingMapper;
import com.nuoxin.virtual.rep.api.service.v3_0.ContentSharingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * 内容分享Impl
 * @author wujiang
 * @date 20190505
 */
@Service
public class ContentSharingServiceImpl implements ContentSharingService {

    @Resource
    private ContentSharingMapper contentSharingMapper;

    private static final Logger logger = LoggerFactory.getLogger(ContentSharingServiceImpl.class);

    @Override
    public PageResponseBean<List<ContentSharingParams>> getContentSharingListPage(ContentSharingRequest contentSharingRequest) {

        List<ContentSharingParams> list = contentSharingMapper.getContentSharingListPage(contentSharingRequest);

        Integer ContentSharingCount = contentSharingMapper.getContentSharingListCount(contentSharingRequest);

        return new PageResponseBean(contentSharingRequest, ContentSharingCount, list);
    }
}
