package com.my.app.api.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.app.api.user.domain.UserRole;
import com.my.app.common.dao.CommonDao;

@Service
public class UserRoleService {

	@Autowired
	private CommonDao commonDao;

	public List<UserRole> getUserRoles() {
		return commonDao.selectList("UserRoleDao.getUserRole");
	}

}
