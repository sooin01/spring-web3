package com.my.app.web.notice.controller.vo;

import com.my.app.web.common.util.CryptoUtils;

import lombok.Data;

@Data
public class NoticeVo {

	private String id;

	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = CryptoUtils.decryptAES(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = CryptoUtils.decryptAES(name);
	}

}
