package com.qiangdong.reader.controller.manage;


import com.qiangdong.reader.entity.Menu;
import com.qiangdong.reader.request.menu.AddOrUpdateMenuRequest;
import com.qiangdong.reader.request.menu.DeleteMenuRequest;
import com.qiangdong.reader.request.menu.ListQueryRequest;
import com.qiangdong.reader.request.menu.ListUserMenuRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.menu.MenuDtoResponse;
import com.qiangdong.reader.response.menu.MenuResponse;
import com.qiangdong.reader.serviceImpl.MenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/manage/menu")
public class ManageMenuController {

    @Autowired
    private MenuServiceImpl menuService;

    @PostMapping("/add-update")
    public Response<Menu> addOrUpdateMenu(@RequestBody AddOrUpdateMenuRequest request) {
        return menuService.addOrUpdateMenu(request);
    }

    @PostMapping("/list")
    public PageResponse<Menu> listMenu(@RequestBody ListQueryRequest request) {
        return menuService.listMenu(request);
    }

    @PostMapping("/user-menu")
    public MenuResponse listUserMenu(@RequestBody ListUserMenuRequest request) {
        return menuService.listUserMenu(request);
    }

    @PostMapping("/delete")
    public Response<String> deleteMenu(@RequestBody DeleteMenuRequest request) {
        return menuService.deleteMenu(request);
    }

    @PostMapping("/enums")
    public MenuDtoResponse enums(@RequestBody ListUserMenuRequest request) {
        return menuService.enums();
    }
}
