package com.nuoxin.virtual.rep.api.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

public class SpeechSplitThread extends Thread {
    @Resource
    private CallBackService callBackService;

    private String ossFilePath;
    private String sinToken;
    private Integer callId;

    public SpeechSplitThread(String ossFilePath,String sinToken, Integer callId){
        this.ossFilePath = ossFilePath;
        this.sinToken = sinToken;
        this.callId = callId;

    }

    @Override
    public void run(){
        //分割录音文件并上传阿里云，返回左右声道的阿里云地址
        Map<String,String> pathMap = callBackService.splitSpeechAliyunUrlUpdate(this.ossFilePath);
        //根据左右声道的阿里云地址进行语音识别，进行入库
        boolean result_is_save = callBackService.saveSpeechRecognitionResultCallInfo(pathMap, this.sinToken);
    }
}
