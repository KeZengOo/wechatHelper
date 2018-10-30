package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import com.nuoxin.virtual.rep.api.dao.DrugUserRepository;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.SaveVirtualDoctorRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * 医生相关单元测试
 * @author tiancun
 * @date 2018-10-29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VirtualDoctorServiceImplTest {

    @Resource
    private DrugUserRepository drugUserRepository;

    /**
     * 单个医生新增
     */
    @Test
    public void saveVirtualDoctor() {

        DrugUser user = drugUserRepository.findFirstByEmail("wei.ruan@naxions.com");
        Assert.assertTrue(user != null && user.getId().equals("11893346") && user.getRoleId().equals("102"));
        SaveVirtualDoctorRequest request = new SaveVirtualDoctorRequest();

        //request.setTelephones();



    }
}