package com.my.app.web.home.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.ibatis.type.Alias;

@Alias("contents")
public class ContentsVo {

	private String contentsId;
	
	private String contentsName;

	public String getContentsId() {
		return contentsId;
	}

	public void setContentsId(String contentsId) {
		this.contentsId = contentsId;
	}

	public String getContentsName() {
		return contentsName;
	}

	public void setContentsName(String contentsName) {
		this.contentsName = contentsName;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
