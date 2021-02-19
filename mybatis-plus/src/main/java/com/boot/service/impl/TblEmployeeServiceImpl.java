package com.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.entity.TblEmployee;
import com.boot.mapper.RealDelMapper;
import com.boot.mapper.TblEmployeeMapper;
import com.boot.service.TblEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author root
 * @since 2020-08-03
 */
@Service
public class TblEmployeeServiceImpl extends ServiceImpl<TblEmployeeMapper, TblEmployee> implements TblEmployeeService {

    @Resource
    private TblEmployeeMapper tblEmployeeMapper;
    @Resource
    private RealDelMapper realDelMapper;

    @Override
    public List<TblEmployee> selAll() {
        return new TblEmployee().selectAll();
    }

    @Cacheable(value = "selById")
    @Override
    public TblEmployee selById(Long id) {
        return this.tblEmployeeMapper.selectById(id);
    }

    @Override
    public List<Map<String, Object>> selByGender(String gender) {
        QueryWrapper<TblEmployee> wrapper = new QueryWrapper<>();
        wrapper.select("id", "gender", "email");
        wrapper.eq("gender", gender);
        return this.tblEmployeeMapper.selectMaps(wrapper);
    }

    @Override
    public IPage<TblEmployee> selEmployeeByPage(Page<TblEmployee> page, Integer state) {
        QueryWrapper<TblEmployee> wrapper = new QueryWrapper<>();
        return this.tblEmployeeMapper.selectPage(page, wrapper);
    }

    @Override
    public Integer delById(Long id) {
        TblEmployee tblEmployee = new TblEmployee();
        tblEmployee.deleteById(5);
        return this.tblEmployeeMapper.deleteById(id);
    }

    @Override
    public Integer insByTblEmployee(TblEmployee tblEmployee) {
        return this.tblEmployeeMapper.insert(tblEmployee);
    }

    @Override
    public Integer realDelById(Long id) {
        return this.realDelMapper.realDelById(id);
    }
}
