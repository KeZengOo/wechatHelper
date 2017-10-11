package com.nuoxin.virtual.rep.api.service;

import com.alibaba.fastjson.JSON;
import com.nuoxin.virtual.rep.api.common.util.StringUtils;
import com.nuoxin.virtual.rep.api.dao.SmsTemplateRepository;
import com.nuoxin.virtual.rep.api.entity.SmsTemplate;
import com.nuoxin.virtual.rep.api.common.bean.TemplateMap;
import com.nuoxin.virtual.rep.api.web.controller.response.SmsTemplateResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fenggang on 9/18/17.
 */
@Service
@Transactional(readOnly = true)
public class SmsTemplateService {

    @Autowired
    private SmsTemplateRepository smsTemplateRepository;

    public SmsTemplate fingById(Long id){
        return smsTemplateRepository.findOne(id);
    }

    public List<SmsTemplateResponseBean> findByAll(){
        List<SmsTemplateResponseBean> responseBeans = new ArrayList<>();
        List<SmsTemplate> list = smsTemplateRepository.findAll();
        if(list!=null && !list.isEmpty()){
            for (int i = 0,leng=list.size(); i < leng; i++) {
                SmsTemplate st = list.get(i);
                SmsTemplateResponseBean bean = new SmsTemplateResponseBean();
                bean.setId(st.getId());
                bean.setMessage(st.getMessage());
                bean.setName(st.getName());
                bean.setTemplate(bean.getTemplate());
                String maps = st.getMaps();
                if(StringUtils.isNotEmtity(maps)){
                    List<TemplateMap> lMaps = JSON.parseArray(maps,TemplateMap.class);
                    if(lMaps!=null && !lMaps.isEmpty()){
                        for (int j = 0,jleng=lMaps.size(); j < jleng; j++) {
                            TemplateMap tm = lMaps.get(j);
                            if(tm.getInput()!=1){
                                lMaps.remove(i);
                            }
                        }
                    }
                    bean.setMaps(lMaps);
                }
                responseBeans.add(bean);
            }
        }
        return responseBeans;
    }
}
