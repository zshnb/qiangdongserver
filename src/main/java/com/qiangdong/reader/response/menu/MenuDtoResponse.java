package com.qiangdong.reader.response.menu;


import com.qiangdong.reader.dto.MenuDto;

import java.util.ArrayList;
import java.util.List;

public class MenuDtoResponse {

    private List<MenuDto> menus = new ArrayList<>();

    public List<MenuDto> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuDto> menus) {
        this.menus = menus;
    }
}
