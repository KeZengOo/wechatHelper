package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.dao.RoleRepository;
import com.nuoxin.virtual.rep.api.dao.RoleUserRepository;
import com.nuoxin.virtual.rep.api.entity.RoleUser;
import com.nuoxin.virtual.rep.api.enums.RoleTypeEnum;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fenggang on 10/18/17.
 */
@Service
public class RoleUserService {

    @Autowired
    private RoleUserRepository roleUserRepository;



    /**
     * 获取企业用户权限
     * @param drugUserId
     * @return
     */
    public List<Long> checkVirtualRole(Long drugUserId){
        List<Long> roleIdList = new ArrayList<>();
        List<RoleUser> list = roleUserRepository.findByUserId(drugUserId);
        if(list!=null && !list.isEmpty()){
            for (RoleUser roleUser:list) {
                if(RoleTypeEnum.SALE.getType().equals(roleUser.getRoleId())
                        || RoleTypeEnum.MANAGER.getType().equals(roleUser.getRoleId())
                        || RoleTypeEnum.PROJECT_MANAGER.getType().equals(roleUser.getRoleId())
                        || RoleTypeEnum.RECRUIT_SALE.getType().equals(roleUser.getRoleId())
                        || RoleTypeEnum.MOBILE_COVER_SALE.getType().equals(roleUser.getRoleId())
                        || RoleTypeEnum.WECHAT_COVER_SALE.getType().equals(roleUser.getRoleId())){
                    roleIdList.add(roleUser.getRoleId());
                }
            }
        }

        if (CollectionsUtil.isEmptyList(roleIdList)){
            throw new BusinessException(ErrorEnum.ERROR, "账号没有权限登录！");
        }

        return roleIdList;
    }
}
