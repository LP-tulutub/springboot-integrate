package com.boot.service.impl;

import com.boot.mapper.AdminMapper;
import com.boot.pojo.Admin;
import com.boot.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Transactional
    @Override
    public Integer insAdmin(Admin admin) {
        return this.adminMapper.insertSelective(admin);
    }
}
