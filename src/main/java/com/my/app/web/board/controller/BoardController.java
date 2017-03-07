package com.my.app.web.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.my.app.web.board.vo.BoardVo;

@Controller
@RequestMapping(value = "/board")
public class BoardController {
	
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);

	@RequestMapping(value = "/index")
	public String index(BoardVo boardVo) {
		log.debug(boardVo.toString());
		return "board/board";
	}

}
