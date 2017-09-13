package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.FileFormatException;
import com.nuoxin.virtual.rep.api.utils.ExcelUtil;
import com.nuoxin.virtual.rep.api.utils.RegularUtils;
import com.nuoxin.virtual.rep.api.web.controller.request.WechatMessageRequestBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 微信相关接口
 */
@Service
public class WechatService {

    private static final String EXTENSION_XLS = "xls";

    private static final String EXTENSION_XLSX = "xlsx";

    private static final String MATCH_TELEPHONE = "^1\\d{10}$";

    //excel从哪行开始读取，这里excel标题占两行
    private static final Integer beginReadRow = 2;


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean importExcel(MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        boolean success = false;
        String fileName = file.getName();
        if (!fileName.endsWith(EXTENSION_XLS) && !fileName.endsWith(EXTENSION_XLSX)){
            throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR);
        }
        String[] split = fileName.split("-");
        if (null == split || split.length < 2){
            throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR);
        }

        String drugUserNickname = split[0];
        String drugUserTelephone = split[1];
        boolean matcher = RegularUtils.isMatcher(MATCH_TELEPHONE, drugUserTelephone);
        if (!matcher){
            throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR);
        }

        String originalFilename = file.getOriginalFilename();
        List<List<String>> lists = ExcelUtil.readExcel(originalFilename, beginReadRow);
        List<WechatMessageRequestBean> wechatMessageList = new ArrayList<>();
        if (null != lists && lists.size() > 0){
            for (List<String> list:lists){
                WechatMessageRequestBean wechatMessageRequestBean = getWechatMessage(list,drugUserNickname,drugUserTelephone);
                wechatMessageList.add(wechatMessageRequestBean);
            }
        }

        return success;
    }


    /**
     * 导入的excel一行为一个对象
     * @param list
     * @return
     */
    private WechatMessageRequestBean getWechatMessage(List<String> list, String drugUserNickname, String drugUserTelephone) {
       if (null == list || list.size() <= 0){
           return null;
       }

       WechatMessageRequestBean wechatMessage = new WechatMessageRequestBean();
       for (int i = 0; i < list.size(); i++){
           String wechatId = list.get(0);
           String wechatTime = list.get(1);
           String nickname = list.get(2);
           String wechatNumber = list.get(3);
           String wechatStatus = list.get(4);
           String wechatMessageType = list.get(5);
           String wechatMessage2 = list.get(6);

       }


       return null;
    }
}
