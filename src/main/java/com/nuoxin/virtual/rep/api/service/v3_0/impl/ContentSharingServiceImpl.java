package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.ContentReadLogsParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.ContentReadLogsTimeParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.ContentSharingParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.ContentReadLogsRequest;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.ContentSharingRequest;
import com.nuoxin.virtual.rep.api.mybatis.ContentSharingMapper;
import com.nuoxin.virtual.rep.api.service.v3_0.ContentSharingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
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

        Integer contentSharingCount = contentSharingMapper.getContentSharingListCount(contentSharingRequest);

        return new PageResponseBean(contentSharingRequest, contentSharingCount, list);
    }

    @Override
    public PageResponseBean<List<ContentReadLogsParams>> getContentReadLogsListPage(ContentReadLogsRequest contentReadLogsRequest) {

        List<ContentReadLogsParams> list = contentSharingMapper.getContentReadLogsListPage(contentReadLogsRequest);

        List<ContentReadLogsParams> newList = new ArrayList<ContentReadLogsParams>();

        for (int i=0; i<list.size(); i++)
        {
            ContentReadLogsParams c = new ContentReadLogsParams();
            c = list.get(i);
            //获取同一名医生多长阅读的时间和时长
            List<ContentReadLogsTimeParams> logsTimeParams = contentSharingMapper.getReadTimeAndReadDurationByDataIdAndDoctorId(list.get(i).getDataId(),list.get(i).getDoctorId());
            String[] createTimeArray = new String[logsTimeParams.size()];
            String[] readTimeArray = new String[logsTimeParams.size()];

            for (int j = 0; j<logsTimeParams.size(); j++){
                createTimeArray[j] = logsTimeParams.get(j).getCreateTime();
                readTimeArray[j] = logsTimeParams.get(j).getReadTime();
            }
            //定义最大值为该数组的第一个数
            int maxIndex = Integer.parseInt(readTimeArray[0]);
            //遍历循环数组
            for (int j = 0; j < readTimeArray.length; j++) {
                if(maxIndex < Integer.parseInt(readTimeArray[j])){
                    maxIndex = Integer.parseInt(readTimeArray[j]);
                }
            }

            c.setCreateTime(createTimeArray);
            c.setReadTime(readTimeArray);
            c.setMaxReadTime(maxIndex+"");
            newList.add(c);
        }

        Integer contentReadLogsCount = contentSharingMapper.getContentReadLogsListCount(contentReadLogsRequest);

        return new PageResponseBean(contentReadLogsRequest, contentReadLogsCount, newList);
    }
}
