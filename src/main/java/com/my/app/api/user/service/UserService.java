package com.my.app.api.user.service;

import static org.jooq.lambda.tuple.Tuple.tuple;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.app.api.user.domain.User;
import com.my.app.api.user.domain.UserRole;
import com.my.app.common.dao.CommonDao;

@Service
public class UserService {

	@Autowired
	private CommonDao commonDao;

	public List<User> getUsers() {
		List<User> userList = commonDao.selectList("UserDao.getUser");
		List<UserRole> userRoleList = commonDao.selectList("UserRoleDao.getUserRole");

		return userList.stream()
			.flatMap(v1 -> userRoleList.stream().map(v2 -> tuple(v1, v2)))
			.filter(t -> t.v1.getUserId().equals(t.v2.getUserId()))
			.map(t -> t.v1)
			.distinct()
			.collect(Collectors.toList());
	}

}
