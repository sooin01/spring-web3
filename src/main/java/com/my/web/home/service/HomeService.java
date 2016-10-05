package com.my.web.home.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.web.common.dao.CommonDao;
import com.my.web.home.vo.MemberVo;

@Service
public class HomeService {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private CommonDao commonDao;

	@Transactional(readOnly = true)
	public List<MemberVo> index() {
		List<MemberVo> memberList = commonDao.selectList("memberDao.list");
		
		for (MemberVo memberVo : memberList) {
			logger.info(memberVo);
		}
		
		return memberList;
	}
	
}
