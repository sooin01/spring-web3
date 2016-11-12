package com.my.app.web.home.vo;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.ibatis.type.Alias;

@Alias("member")
public class MemberVo {
	
	private String memberId;
	
	private String memberName;
	
	private List<ContentsVo> contentsList;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public List<ContentsVo> getContentsList() {
		return contentsList;
	}

	public void setContentsList(List<ContentsVo> contentsList) {
		this.contentsList = contentsList;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
