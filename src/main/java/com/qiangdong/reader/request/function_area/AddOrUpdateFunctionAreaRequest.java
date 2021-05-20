package com.qiangdong.reader.request.function_area;

import com.qiangdong.reader.request.BaseRequest;

public class AddOrUpdateFunctionAreaRequest extends BaseRequest {
	private Long functionAreaId = 0L;

	private Long parentId = 0L;

	private String name = "";

	private String icon = "";

	public Long getFunctionAreaId() {
		return functionAreaId;
	}

	public void setFunctionAreaId(Long functionAreaId) {
		this.functionAreaId = functionAreaId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
