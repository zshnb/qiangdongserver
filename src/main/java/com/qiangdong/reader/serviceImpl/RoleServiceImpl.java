package com.qiangdong.reader.serviceImpl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.dao.RoleMapper;
import com.qiangdong.reader.entity.Role;
import com.qiangdong.reader.service.IRoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService  {
}
