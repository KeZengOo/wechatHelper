package com.nuoxin.virtual.rep.api.service;

import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.mybatis.DoctorCallInfoMapper;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.web.controller.response.call.CallInfoResponseBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author tiancun
 * @date 2018-07-20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DoctorCallServiceTest {

    @Autowired
    private OssService ossService;

    @Resource
    private FileService fileService;

    @Resource
    private DoctorCallInfoMapper doctorCallInfoMapper;

    @Test
    public void uploadUrl() {
        String path = "D:\\wav\\";
//        fileService.downLoadFromUrl("http://106.75.91.226/16?file=/app/clpms/record/20171211/103_nxclcc_8001_13799438628_20171211185149_1512989509171.wav","test.wav",path);
//        File file = new File(path+"test.wav");
//        String url = ossService.uploadFile(file);


        List<CallInfoResponseBean> callInfoList = doctorCallInfoMapper.getCallInfoList();
        if (CollectionsUtil.isEmptyList(callInfoList)){
            return;
        }

        for (CallInfoResponseBean callInfoResponseBean: callInfoList){
            Long id = callInfoResponseBean.getId();
            String infoJson = callInfoResponseBean.getInfoJson();
            String sinToken = callInfoResponseBean.getSinToken();
            JSONObject jsonObject = JSONObject.parseObject(infoJson);
            if (jsonObject == null){
                doctorCallInfoMapper.updateCallUrl(null, id);
                System.out.println("更新完毕， id = " + id);
                continue;
            }
            Integer mediano = jsonObject.getInteger("mediano");
            if (mediano == null || mediano == 0){
                doctorCallInfoMapper.updateCallUrl(null, id);
                System.out.println("更新完毕， id = " + id);
                continue;
            }
            String recordUrl = jsonObject.getString("recordUrl");
            if (StringUtils.isEmpty(recordUrl)){
                doctorCallInfoMapper.updateCallUrl(null, id);
                System.out.println("更新完毕， id = " + id);
                continue;
            }
//            String uploadUrl = "http://106.75.91.226/"+ mediano +"?file="+recordUrl;
//            System.out.println("uploadUrl= " + uploadUrl);
//            //fileService.downLoadFromUrl(uploadUrl,sinToken +".wav",path);
//            downloadFile(uploadUrl, path + sinToken + ".wav");
//            File file = new File(path + sinToken +".wav");
//            String url = ossService.uploadFile(file);
//            if (url.contains("\\")){
//                url = url.replaceAll("\\\\","/");
//            }
//            if (!StringUtils.isEmpty(url)){
//                doctorCallInfoMapper.updateCallUrl(url, id);
//            }

            //doctorCallInfoMapper.updateCallUrl(null, id);
            // System.out.println("url= " + url);

        }

    }



    public static void downloadFile(String remoteFilePath, String localFilePath){
        URL urlfile = null;
        HttpURLConnection httpUrl = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        File f = new File(localFilePath);
        try
        {
            urlfile = new URL(remoteFilePath);
            httpUrl = (HttpURLConnection)urlfile.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(f));
            int len = 2048;
            byte[] b = new byte[len];
            while ((len = bis.read(b)) != -1)
            {
                bos.write(b, 0, len);
            }
            System.out.println("上传成功");
            bos.flush();
            bis.close();
            httpUrl.disconnect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                bis.close();
                bos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}