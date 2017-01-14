package com.my.app.web.notice.controller.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.my.app.web.common.util.CryptoUtils;

public class NoticeVo {

	private String id;

	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		System.out.println("id: " + id);
		this.id = CryptoUtils.decryptAES(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		System.out.println("name: " + name);
		this.name = CryptoUtils.decryptAES(name);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
