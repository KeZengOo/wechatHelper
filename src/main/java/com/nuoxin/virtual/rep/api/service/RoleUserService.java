package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.dao.RoleUserRepository;
import com.nuoxin.virtual.rep.api.entity.RoleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                if(roleUser.getRoleId()==101 || roleUser.getRoleId()==102){
                    return roleUser.getRoleId();
                }
            }
        }
        return null;
    }
}
