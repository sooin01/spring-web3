package com.my.app.web.board.vo;

import com.my.app.web.common.util.CryptoUtils;

import lombok.Data;

@Data
public class BoardVo {

	private String id;

	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = CryptoUtils.decryptUtf8Aes(id);
//		this.id = CryptoUtils.decryptHexAes(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = CryptoUtils.decryptUtf8Aes(name);
//		this.name = CryptoUtils.decryptHexAes(name);
	}

}
