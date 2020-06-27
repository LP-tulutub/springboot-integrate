package com.boot.service;

import com.boot.pojo.Admin;

public interface AdminService {

    Integer insAdmin(Admin admin);

    Integer updAdminById(Admin admin);

    Integer delAdminByIdExist(Admin admin);

}
