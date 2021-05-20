package com.qiangdong.reader.response.menu;

import com.qiangdong.reader.entity.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuResponse {

    private List<Menu> menus = new ArrayList<>();

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
}
