package com.qiangdong.reader.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.dto.MenuDto;
import com.qiangdong.reader.entity.Menu;
import com.qiangdong.reader.enums.user.UserRoleEnum;
import com.qiangdong.reader.request.menu.AddOrUpdateMenuRequest;
import com.qiangdong.reader.request.menu.DeleteMenuRequest;
import com.qiangdong.reader.request.menu.ListQueryRequest;
import com.qiangdong.reader.request.menu.ListUserMenuRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.menu.MenuDtoResponse;
import com.qiangdong.reader.response.menu.MenuResponse;

import java.util.List;

public interface IMenuService extends IService<Menu> {

    Response<Menu> addOrUpdateMenu(AddOrUpdateMenuRequest request);

    PageResponse<Menu> listMenu(ListQueryRequest request);

    MenuResponse listUserMenu(ListUserMenuRequest listUserMenuRequest);

    Response<String> deleteMenu(DeleteMenuRequest request);

    MenuDtoResponse enums();
}
