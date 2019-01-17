package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.util.PasswordEncoder;
import com.nuoxin.virtual.rep.api.dao.DrugUserRepository;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.ProductLine;
import com.nuoxin.virtual.rep.api.mybatis.DrugUserMapper;
import com.nuoxin.virtual.rep.api.web.controller.request.QueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.UpdatePwdRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.DrugUserResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorStatResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fenggang on 9/11/17.
 */
@Service
@Transactional(readOnly = true)
public class DrugUserService {

    @Autowired
    private DrugUserRepository drugUserRepository;
    @Autowired
    private DrugUserMapper drugUserMapper;
    @Autowired
    private ProductLineService productLineService;

    /**
     * 根据邮箱查询企业用户
     * @param email
     * @return
     */
    public DrugUser findByEmail(String email){
        return drugUserRepository.findFirstByEmail(email);
    }

    /**
     * 根据id查询企业用户
     * @param drugUserId
     * @return
     */
    @Cacheable(value="virtual_app_web_druguser",key = "'_findById_'+#drugUserId")
    public DrugUser findById(Long drugUserId){
        return drugUserRepository.findOne(drugUserId);
    }

    /**
     * 修改企业用户密码
     * @param bean
     */
    @Transactional(readOnly = false)
    public void updatePawd(UpdatePwdRequestBean bean){
        DrugUser user = this.findByEmail(bean.getEmail());
        if(user!=null){
            String newPwd = PasswordEncoder.encode(bean.getPassword());
            user.setPassword(newPwd);
            drugUserRepository.saveAndFlush(user);
        }
    }

    /**
     * 获取leaderpath下面关联productId的企业用户
     * @param leaderPath
     * @param productId
     * @return
     */
    public List<DrugUserResponseBean> relationDrugUser(String leaderPath,Long productId){
        return drugUserMapper.relationDrugUser(leaderPath,productId);
    }




    /**
     * 获取doctor集合
     * @param bean
     * @return
     */
    public List<DoctorResponseBean> doctorPage(QueryRequestBean bean){
        return drugUserMapper.doctorPage(bean);
    }

    public Integer doctorPageCount(QueryRequestBean bean){
        return drugUserMapper.doctorPageCount(bean);
    }

    /**
     * 根据id获取product key word
     * @param drugUserId
     * @return
     */
    @Cacheable(value="virtual_app_web_druguser",key = "'_findByProductKeyWord_'+#drugUserId")
    public List<ProductLine> findByProductKeyWord(Long drugUserId){
        List<ProductLine> result = new ArrayList<>();
        DrugUser drugUser = drugUserRepository.findFirstById(drugUserId);
        String leaderPath = "";
        if (null != drugUser){
            leaderPath = drugUser.getLeaderPath();
        }
        List<Long> list = productLineService.getProductIds(leaderPath);
        if(list!=null && !list.isEmpty()){
            for (Long l:list) {
                ProductLine productLine = productLineService.findById(l);
                if(productLine!=null){
                    String ckeyWord = productLine.getCkeyWord();
                    if(!StringUtils.isEmpty(ckeyWord)){
                        productLine.setCkeyWord(ckeyWord.replaceAll("，",","));
                    }
                    String pkeyWord = productLine.getPkeyWord();
                    if (!StringUtils.isEmpty(pkeyWord)){
                        productLine.setPkeyWord(pkeyWord.replaceAll("，",","));
                    }


//                    productLine.setCkeyWord(productLine.getCkeyWord().replaceAll("，",","));
//                    productLine.setPkeyWord(productLine.getPkeyWord().replaceAll("，",","));
                    result.add(productLine);
                }
            }
        }
        return result;
    }

}
