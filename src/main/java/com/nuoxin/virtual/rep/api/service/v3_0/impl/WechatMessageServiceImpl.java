package com.nuoxin.virtual.rep.api.service.v3_0.impl;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.mybatis.VirtualWechatMessageMapper;
import com.nuoxin.virtual.rep.api.service.v3_0.WechatMessageService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.utils.ExportExcelUtil;
import com.nuoxin.virtual.rep.api.utils.ExportExcelWrapper;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.WechatMessageRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.ExportDrugUserDoctorCallResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.WechatMessageSummaryResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 微信消息业务相关实现
 * @author tiancun
 * @date 2019-05-14
 */
@Service
public class WechatMessageServiceImpl implements WechatMessageService {

    @Resource
    private VirtualWechatMessageMapper virtualWechatMessageMapper;

    @Override
    public PageResponseBean<WechatMessageSummaryResponse> getWechatMessageSummaryPage(WechatMessageRequest request) {

        Integer count = virtualWechatMessageMapper.getWechatMessageSummaryListCount(request);
        if (count == null){
            count = 0;
        }

        List<WechatMessageSummaryResponse> messageSummaryList = null;
        if (count > 0){
            messageSummaryList = virtualWechatMessageMapper.getWechatMessageSummaryList(request);
        }

        return new PageResponseBean<>(request, count, messageSummaryList);
    }



    @Override
    public void exportWechatMessageSummaryList(HttpServletResponse response, WechatMessageRequest request) {
        request.setPaginable(1);
        List<WechatMessageSummaryResponse> messageSummaryList = virtualWechatMessageMapper.getWechatMessageSummaryList(request);
        if (CollectionsUtil.isEmptyList(messageSummaryList)){
            throw new BusinessException(ErrorEnum.ERROR, "暂无数据！");
        }


        ExportExcelWrapper<WechatMessageSummaryResponse> exportExcelWrapper = new ExportExcelWrapper();
        exportExcelWrapper.exportExcel("微信消息概要查询", "微信消息概要查询",
                new String[]{"代表ID", "代表姓名", "角色名称", "拜访方式", "1是和医生聊天，2是群聊", "医生ID", "群聊ID", "医生姓名或者群名称",
                "医院ID", "医院名称", "医生科室", "招募状态", "覆盖状态", "总对话数", "代表对话数量", "医生对话数量", "上一次微信导入时间"},
                messageSummaryList, response, ExportExcelUtil.EXCEl_FILE_2007);

    }
}
