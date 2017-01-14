package com.my.app.web.notice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.my.app.web.notice.controller.vo.NoticeVo;

@Controller
@RequestMapping(value = "/notice")
public class NoticeController {

	@RequestMapping(value = "/index")
	public String index(NoticeVo noticeVo) {
		System.out.println(noticeVo);
		return "notice/notice";
	}

}
