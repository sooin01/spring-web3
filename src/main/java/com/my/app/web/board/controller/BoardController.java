package com.my.app.web.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.my.app.web.board.vo.BoardVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/board")
public class BoardController {

	@RequestMapping(value = "/index")
	public String index(BoardVo boardVo) {
		log.debug(boardVo.toString());
		return "board/board";
	}

}
