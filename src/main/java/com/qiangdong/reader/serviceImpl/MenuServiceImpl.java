package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.dao.MenuMapper;
import com.qiangdong.reader.dto.MenuDto;
import com.qiangdong.reader.entity.Menu;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.enums.user.UserRoleEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.menu.AddOrUpdateMenuRequest;
import com.qiangdong.reader.request.menu.DeleteMenuRequest;
import com.qiangdong.reader.request.menu.ListQueryRequest;
import com.qiangdong.reader.request.menu.ListUserMenuRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.menu.MenuDtoResponse;
import com.qiangdong.reader.response.menu.MenuResponse;
import com.qiangdong.reader.service.IMenuService;
import com.qiangdong.reader.service.IUserService;
import com.qiangdong.reader.utils.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    private final PageUtil pageUtil;
    private final MenuMapper menuMapper;
    private final IUserService userService;

    public MenuServiceImpl(PageUtil pageUtil, MenuMapper menuMapper,
                           IUserService userService) {
        this.pageUtil = pageUtil;
        this.menuMapper = menuMapper;
        this.userService = userService;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Response<Menu> addOrUpdateMenu(AddOrUpdateMenuRequest request) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(request, menu);
        if (request.getId() == null) {
            menuMapper.insert(menu);
        } else {
            menuMapper.updateById(menu);
        }
        return Response.ok(menu);
    }

    @Override
    public PageResponse<Menu> listMenu(ListQueryRequest request) {
        IPage<Menu> menuIPage = page(pageUtil.of(request));
        return PageResponse.of(menuIPage, request.getPageSize());
    }

    @Override
    public MenuResponse listUserMenu(ListUserMenuRequest request) {
        User user = userService.getById(request.getUserId());
        List<Menu> menus = list(new QueryWrapper<Menu>()
            .eq("role", user.getRole()));
        MenuResponse menuResponse = new MenuResponse();
        menuResponse.setMenus(menus);
        return menuResponse;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Response<String> deleteMenu(DeleteMenuRequest request) {
        menuMapper.deleteById(request.getMenuId());
        return Response.ok();
    }

    @Override
    public MenuDtoResponse enums() {
        MenuDtoResponse menuDtoResponse = new MenuDtoResponse();
        menuDtoResponse.setMenus(menuMapper.findMenuEnumList());
        return menuDtoResponse;
    }

}
