package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.common.util.PasswordEncoder;
import com.nuoxin.virtual.rep.api.dao.RoleRepository;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.Role;
import com.nuoxin.virtual.rep.api.mybatis.DrugUserMapper;
import com.nuoxin.virtual.rep.api.web.controller.request.LoginRequestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by fenggang on 9/11/17.
 * @author fenggang
 */
@Service
public class LoginService {

    @Autowired
    private DrugUserService drugUserService;
    @Autowired
    private RoleUserService roleUserService;

    @Resource
    private DrugUserMapper drugUserMapper;


	public DrugUser login(LoginRequestBean bean) {
		DrugUser drugUser = drugUserService.findByEmail(bean.getUserName());
		if (drugUser == null) {
			throw new BusinessException(ErrorEnum.LOGIN_ERROR.getStatus(), "用户名不存在");
		}
		
		if (!PasswordEncoder.checkPassword(bean.getPassword(), drugUser.getPassword())) {
			throw new BusinessException(ErrorEnum.LOGIN_ERROR.getStatus(), "用户名密码不匹配");
		}
		
		drugUser.setLeaderPath(drugUser.getLeaderPath() + "%");
		List<Long> roleIdList = roleUserService.checkVirtualRole(drugUser.getId());
		drugUser.setRoleIdList(roleIdList);
		String roleName = drugUserMapper.getRoleNameByUserId(drugUser.getId());
		drugUser.setRoleName(roleName);

		return drugUser;
	}
}
