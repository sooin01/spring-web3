package com.my.app.web.home.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.app.web.common.dao.CommonDao;
import com.my.app.web.home.vo.MemberVo;

@Service
public class HomeService {
	
	private static final Logger log = LogManager.getLogger();
	
	@Autowired
	private CommonDao commonDao;
	
	@Transactional(readOnly = true)
//	@Cacheable("commons")
	public List<MemberVo> list() {
		return commonDao.selectList("MemberDao.list");
	}
	
}
