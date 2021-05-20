package com.qiangdong.reader.request.menu;


import com.qiangdong.reader.request.BaseRequest;

public class DeleteMenuRequest extends BaseRequest {
    private Long menuId;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}
