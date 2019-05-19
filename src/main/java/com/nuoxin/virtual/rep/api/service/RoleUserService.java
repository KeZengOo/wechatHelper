package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.dao.RoleRepository;
import com.nuoxin.virtual.rep.api.dao.RoleUserRepository;
import com.nuoxin.virtual.rep.api.entity.RoleUser;
import com.nuoxin.virtual.rep.api.enums.RoleTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public Long checkVirtualRole(Long drugUserId){
        List<RoleUser> list = roleUserRepository.findByUserId(drugUserId);
        if(list!=null && !list.isEmpty()){
            for (RoleUser roleUser:list) {
                if(RoleTypeEnum.SALE.getType().equals(roleUser.getRoleId())
                        || RoleTypeEnum.MANAGER.getType().equals(roleUser.getRoleId())
                        || RoleTypeEnum.PROJECT_MANAGER.getType().equals(roleUser.getRoleId())
                        || RoleTypeEnum.RECRUIT_SALE.getType().equals(roleUser.getRoleId())
                        || RoleTypeEnum.COVER_SALE.getType().equals(roleUser.getRoleId())){
                    return roleUser.getRoleId();
                }
            }
        }
        return null;
    }
}
