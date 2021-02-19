package com.boot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.entity.TblEmployee;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author root
 * @since 2020-08-03
 */
public interface TblEmployeeService extends IService<TblEmployee> {

    List<TblEmployee> selAll();

    TblEmployee selById(Long id);

    List<Map<String, Object>> selByGender(String gender);

    IPage<TblEmployee> selEmployeeByPage(Page<TblEmployee> page, Integer state);

    Integer delById(Long id);

    Integer insByTblEmployee(TblEmployee tblEmployee);

    Integer realDelById(Long id);
}
