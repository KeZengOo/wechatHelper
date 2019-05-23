package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.*;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.ContentReadLogsRequest;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.ContentSharingRequest;
import com.nuoxin.virtual.rep.api.mybatis.ContentSharingMapper;
import com.nuoxin.virtual.rep.api.service.v3_0.ContentSharingService;
import com.nuoxin.virtual.rep.api.utils.ParseTimeSecondsUtils;
import com.nuoxin.virtual.rep.api.utils.csv.CSVUtils;
import com.nuoxin.virtual.rep.api.utils.csv.PublicGlobalCSVExprot;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
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

        //代表数组转list
        List<Long> drugUserIds = new ArrayList<Long>();
        if(contentSharingRequest.getDrugUserId()!= null){
            drugUserIds = Arrays.asList(contentSharingRequest.getDrugUserId());
        }

        List<ContentSharingParams> list = contentSharingMapper.getContentSharingListPage(contentSharingRequest,drugUserIds);
        List<ContentSharingParams> newList = new ArrayList<ContentSharingParams>();

        list.forEach(n->{
            List<ContentSharingParams> roleNames =  contentSharingMapper.getContentSharingRoleNameByDrugUserId(n.getDrugUserId());
            String roleNamesString = "";
            for (int i=0; i<roleNames.size(); i++)
            {
                roleNamesString += roleNames.get(i).getRoleName()+",";
            }

            //该代表的文章的医生阅读数
            Integer readCount = contentSharingMapper.getReadCountByDrugUserAndTitle(n.getId(),n.getDrugUserId(),n.getShareType());
            logger.info("titleID:"+ n.getId()+"readCount:"+readCount);
            ContentSharingParams c = new ContentSharingParams();
            c = n;
            c.setTime(n.getTime().substring(0,n.getTime().indexOf(".")));
            c.setPeopleNumber(readCount);
            c.setRoleName(roleNamesString.substring(0,roleNamesString.length()));
            c.setTotalDuration(ParseTimeSecondsUtils.secondToTime(Long.parseLong(n.getTotalDuration())));
            newList.add(c);
        });

        Integer contentSharingCount = contentSharingMapper.getContentSharingListCount(contentSharingRequest,drugUserIds);

        return new PageResponseBean(contentSharingRequest, contentSharingCount, newList);
    }

    @Override
    public PageResponseBean<List<ContentReadLogsParams>> getContentReadLogsListPage(ContentReadLogsRequest contentReadLogsRequest) {

        List<Long> drugUserIds = new ArrayList<Long>();
        if(contentReadLogsRequest.getDrugUserId()!= null){
            drugUserIds = Arrays.asList(contentReadLogsRequest.getDrugUserId());
        }

        List<ContentReadLogsParams> list = contentSharingMapper.getContentReadLogsListPage(contentReadLogsRequest);

        List<ContentReadLogsParams> newList = new ArrayList<ContentReadLogsParams>();

        for (int i=0; i<list.size(); i++)
        {
            ContentReadLogsParams c = new ContentReadLogsParams();
            c = list.get(i);
            //获取同一名医生多长阅读的时间和时长
            List<ContentReadLogsTimeParams> logsTimeParams = contentSharingMapper.getReadTimeAndReadDurationByDataIdAndDoctorId(list.get(i).getDataId(),list.get(i).getDoctorId(), contentReadLogsRequest.getShareType(), contentReadLogsRequest.getDrugUserId());
            String[] createTimeArray = new String[logsTimeParams.size()];
            String[] readTimeArray = new String[logsTimeParams.size()];
            String[] readTimeStringArray = new String[logsTimeParams.size()];

            for (int j = 0; j<logsTimeParams.size(); j++){
                createTimeArray[j] = logsTimeParams.get(j).getCreateTime().substring(0,logsTimeParams.get(j).getCreateTime().indexOf("."));
                readTimeArray[j] = logsTimeParams.get(j).getReadTime();
                readTimeStringArray[j] = ParseTimeSecondsUtils.secondToTime(Long.parseLong(logsTimeParams.get(j).getReadTime()));
            }
            //定义最大值为该数组的第一个数
            int maxIndex = 0;
                if(readTimeArray.length > 0){
                    maxIndex = Integer.parseInt(readTimeArray[0]);
                    //遍历循环数组
                    for (int j = 0; j < readTimeArray.length; j++) {
                        if(maxIndex < Integer.parseInt(readTimeArray[j])){
                            maxIndex = Integer.parseInt(readTimeArray[j]);
                        }
                    }
                }

            c.setCreateTime(createTimeArray);
            c.setReadTime(readTimeStringArray);
            c.setMaxReadTime(ParseTimeSecondsUtils.secondToTime(maxIndex));
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
            //代表数组转list
            List<Long> drugUserIds = new ArrayList<Long>();
            drugUserIds = Arrays.asList(contentSharingRequest.getDrugUserId());

            list = contentSharingMapper.getContentSharingList(contentSharingRequest,drugUserIds);

            //时间格式转字符串
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for(int i = 0; i < list.size(); i++){
                Long titleId = Long.parseLong(list.get(i).get("id").toString());
                Long drugUserId = Long.parseLong(list.get(i).get("drugUserId").toString());
                Integer shareType = Integer.parseInt(list.get(i).get("shareType").toString());
                //该代表的文章的医生阅读数
                Integer readCount = contentSharingMapper.getReadCountByDrugUserAndTitle(titleId,drugUserId,shareType);
                logger.info("titleID:"+ titleId+"readCount:"+readCount);

                if(list.get(i).get("saleType").equals(0)){
                    list.get(i).replace("saleType",0,"没有类型为经理");
                }
                else if(list.get(i).get("saleType").equals(1)){
                    list.get(i).replace("saleType",1,"线上");
                }
                else if(list.get(i).get("saleType").equals(2)){
                    list.get(i).replace("saleType",2,"线下");
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

    @Override
    public void contentSharingExportFile(Integer productId, Long[] drugUserId, String startTimeAfter, String startTimeBefore, Integer shareType, String title, HttpServletResponse response) {
        //代表数组转list
        List<Long> drugUserIds = new ArrayList<Long>();
        if(null != drugUserId && drugUserId.length > 0){
            drugUserIds = Arrays.asList(drugUserId);
        }
        List<ContentSharingParams> list = contentSharingMapper.getContentSharingCSVList(productId, drugUserIds, startTimeAfter, startTimeBefore, shareType, title);
        List<ContentSharingExcelParams> newList = new ArrayList<ContentSharingExcelParams>();
        //时间格式转字符串
        HashMap map = new LinkedHashMap();
        map.put("1", "ID");
        map.put("2", "标题");
        map.put("3", "发布时间");
        map.put("4", "分享渠道");
        map.put("5", "代表");
        map.put("6", "所属产品");
        map.put("7", "拜访方式");
        map.put("8", "阅读人数");
        map.put("9", "阅读总时长-秒");
        String fileds[] = new String[] { "id", "title", "time", "shareType", "drugUserName", "prodName", "saleType", "peopleNumber", "totalDuration"};

        for(int i = 0; i < list.size(); i++){

            Long titleId = Long.parseLong(list.get(i).getId().toString());
            Long drugUserIdParam = Long.parseLong(list.get(i).getDrugUserId().toString());
            Integer shareTypeParam = Integer.parseInt(list.get(i).getShareType().toString());
            //该代表的文章的医生阅读数
            Integer readCount = contentSharingMapper.getReadCountByDrugUserAndTitle(titleId,drugUserIdParam,shareTypeParam);
            logger.info("titleID:"+ titleId+"readCount:"+readCount);

            ContentSharingExcelParams contentSharingExcelParams = new ContentSharingExcelParams();
            contentSharingExcelParams.setId(list.get(i).getId());
            contentSharingExcelParams.setTitle(list.get(i).getTitle());
            contentSharingExcelParams.setTime(list.get(i).getTime().substring(0,list.get(i).getTime().indexOf(".")));
            contentSharingExcelParams.setDrugUserName(list.get(i).getDrugUserName());
            contentSharingExcelParams.setProdName(list.get(i).getProdName());
            contentSharingExcelParams.setPeopleNumber(readCount.toString());
            contentSharingExcelParams.setTotalDuration(list.get(i).getTotalDuration());

            if(list.get(i).getSaleType().equals(0)){
                contentSharingExcelParams.setSaleType("没有类型为经理");
            }
            else if(list.get(i).getSaleType().equals(1)){
                contentSharingExcelParams.setSaleType("线上");
            }
            else if(list.get(i).getSaleType().equals(2)){
                contentSharingExcelParams.setSaleType("线下");
            }

            if(list.get(i).getShareType().equals(1)){
                contentSharingExcelParams.setShareType("微信");
            }
            else if(list.get(i).getShareType().equals(2)){
                contentSharingExcelParams.setShareType("短信");
            }
            else if(list.get(i).getShareType().equals(3)){
                contentSharingExcelParams.setShareType("邮件");
            }
            else if(list.get(i).getShareType().equals(4)){
                contentSharingExcelParams.setShareType("小程序");
            }
            newList.add(contentSharingExcelParams);
        }

        //调用导出CSV文件公共方法
        PublicGlobalCSVExprot.exportCSVFile(response,map,newList,fileds,"contentSharing.csv");
    }


}
