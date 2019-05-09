package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.ContentReadLogsParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.ContentReadLogsTimeParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.ContentSharingParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.FilePathParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.ContentReadLogsRequest;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.ContentSharingRequest;
import com.nuoxin.virtual.rep.api.mybatis.ContentSharingMapper;
import com.nuoxin.virtual.rep.api.service.v3_0.ContentSharingService;
import com.nuoxin.virtual.rep.api.utils.csv.CSVUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 内容分享Impl
 * @author wujiang
 * @date 20190505
 */
@Slf4j
@Service
public class ContentSharingServiceImpl implements ContentSharingService {

    @Resource
    private ContentSharingMapper contentSharingMapper;

    private static final Logger logger = LoggerFactory.getLogger(ContentSharingServiceImpl.class);

    @Value("${file.temp}")
    private String fileTemp;

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

    @Override
    public FilePathParams exportFile(ContentSharingRequest contentSharingRequest, HttpServletResponse response) {

        BufferedWriter csvFileOutputStream=null;
        String outPutPath = fileTemp;
        String fileName="contentSharing.csv";

        try {
            //获取导出标题
            LinkedHashMap<String,String> titleMap=new LinkedHashMap<>();
            titleMap.put("id","ID");
            titleMap.put("title","标题");
            titleMap.put("time","发布时间");
            titleMap.put("shareType","分享渠道");
            titleMap.put("drugUserName","代表");
            titleMap.put("prodName","所属产品");
            titleMap.put("saleType","拜访方式");
            titleMap.put("peopleNumber","阅读人数");
            titleMap.put("totalDuration","阅读总时长");

            //创建csv文件
            csvFileOutputStream= CSVUtils.createCSVFile(titleMap, outPutPath,fileName);

            //如果hcp_id_temp表中没有数据，那么详情页面不显示数据
            List<Map<String,Object>>  list = Collections.emptyList();

            list = contentSharingMapper.getContentSharingList(contentSharingRequest);

            //时间格式转字符串
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for(int i = 0; i < list.size(); i++){
                if(list.get(i).get("saleType").equals(0)){
                    list.get(i).replace("saleType",0,"没有类型为经理");
                }
                else if(list.get(i).get("saleType").equals(1)){
                    list.get(i).replace("saleType",1,"是线上销售");
                }
                else if(list.get(i).get("saleType").equals(2)){
                    list.get(i).replace("saleType",2,"是线下销售");
                }

                if(list.get(i).get("shareType").equals(1)){
                    list.get(i).replace("shareType",1,"微信");
                }
                else if(list.get(i).get("shareType").equals(2)){
                    list.get(i).replace("shareType",2,"短信");
                }
                else if(list.get(i).get("shareType").equals(3)){
                    list.get(i).replace("shareType",3,"邮件");
                }

                String timeStr = simpleDateFormat.format(list.get(i).get("time"));
                list.get(i).replace("time",list.get(i).get("time"),timeStr);
            }

            //为CSV文件添加数据
            CSVUtils.createData(list,titleMap,csvFileOutputStream);
            //清空缓冲区数据
            CSVUtils.fileFlush(csvFileOutputStream);
        } catch (Exception e) {
            log.error("Exception",e);
        } finally {
            try {
                csvFileOutputStream.close();
            } catch (IOException e) {
                log.error("IOException",e);
            }
        }
        FilePathParams filePathParams =new FilePathParams();
        filePathParams.setFileName(fileName);
        filePathParams.setOutPutPath(outPutPath);
        return filePathParams;
    }

    @Override
    public void download(String outPutPath, String fileName, HttpServletResponse response) {
        try {
            //导出数据
            CSVUtils.exportFile(response,outPutPath,fileName);
            //清除临时文件
            CSVUtils.deleteFile(outPutPath,fileName);
        } catch (IOException e) {
            log.error("IOException",e);
        }
    }
}
