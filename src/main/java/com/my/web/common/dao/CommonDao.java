package com.my.web.common.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommonDao {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public <T> List<T> selectList(String statement) {
		return sqlSession.selectList(statement);
	}
	
	public <T> List<T> selectList(String statement, Object parameter) {
		return sqlSession.selectList(statement, parameter);
	}
	
}
