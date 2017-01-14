package com.my.app.web.notice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.my.app.web.notice.controller.vo.NoticeVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/notice")
public class NoticeController {

	@RequestMapping(value = "/index")
	public String index(NoticeVo noticeVo) {
		log.debug(noticeVo.toString());
		return "notice/notice";
	}

}
