package com.nuoxin.virtual.rep.api.service;

import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.DoctorVo;
import com.nuoxin.virtual.rep.api.entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by fenggang on 9/15/17.
 */
@Service
public class CenterDataService {

    @Value("${data.center.prefix.url}")
    private String prefixUrl;

    @Autowired
    private RestTemplate restTemplate;

    public DoctorVo checkout(Doctor doctor){
        String pathUrl = prefixUrl+"check/one/doctor";
        DoctorVo bean = new DoctorVo();
        bean.setCity(doctor.getCity());
        bean.setDepart(doctor.getDepartment());
        bean.setHospitalId(doctor.getHospitalId());
        bean.setHospitalName(doctor.getHospitalName());
        bean.setProvice(doctor.getProvince());
        bean.setPositions(doctor.getDoctorLevel());
        HttpEntity<String> formEntity = this.getHttpEntity(bean);
        DefaultResponseBean<DoctorVo> responseBean = restTemplate.postForEntity( pathUrl,
                formEntity, DefaultResponseBean.class).getBody();
        if(responseBean.getCode()==200){
            return responseBean.getData();
        }
        return null;
    }

    private HttpEntity<String> getHttpEntity(Object bean){
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(JSONObject.toJSONString(bean), headers);
        return formEntity;
    }

}
