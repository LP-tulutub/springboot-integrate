package com.boot.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.boot.mapper.AdminMapper;
import com.boot.pojo.Admin;
import com.boot.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Service(interfaceClass = AdminService.class)
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Transactional
    @Override
    public Integer insAdmin(Admin admin) {
        return this.adminMapper.insert(admin);
    }

    @Transactional
    @Override
    public Integer updAdminById(Admin admin) {
        return this.adminMapper.updateById(admin);
    }

    @Transactional
    @Override
    public Integer delAdminByIdExist(Admin admin) {

        Admin exist = this.adminMapper.selectById(admin.getId());
        if (exist == null) return 1;
        return this.adminMapper.deleteById(admin.getId());
    }


}
