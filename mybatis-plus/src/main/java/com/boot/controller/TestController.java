package com.boot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.entity.TblEmployee;
import com.boot.service.TblEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/plus")
public class TestController {

    @Autowired
    private TblEmployeeService tblEmployeeServiceImpl;

    /**
     * 对象 AR 查询
     * @return
     */
    @RequestMapping("/test01")
    @ResponseBody
    public Object test01(){
        return this.tblEmployeeServiceImpl.selAll();
    }

    /**
     * mapper 查询
     * @param id
     * @return
     */
    @RequestMapping("/test02/{id}")
    @ResponseBody
    public Object test02(@PathVariable Long id){
        return this.tblEmployeeServiceImpl.selById(id);
    }

    /**
     * 条件查询
     * @param gender
     * @return
     */
    @RequestMapping("/test03/{gender}")
    @ResponseBody
    public Object test02(@PathVariable String gender){
        return this.tblEmployeeServiceImpl.selByGender(gender);
    }

    /**
     * 分页功能
     * @param page
     * @return
     */
    @RequestMapping("/test04/{page}")
    @ResponseBody
    public Object test04(@PathVariable Long page){
        Page<TblEmployee> tblEmployeePage = new Page<>();
        tblEmployeePage.setSize(2); // 设置每页数量
        tblEmployeePage.setCurrent(page); // 当前页码
        return this.tblEmployeeServiceImpl.selEmployeeByPage(tblEmployeePage, 1);
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @RequestMapping("/test05/{id}")
    @ResponseBody
    public Object test05(@PathVariable Long id){
        return this.tblEmployeeServiceImpl.delById(id);
    }

    /**
     * 逻辑删除下的插入
     * 自动填充
     * @return
     */
    @RequestMapping("/test06")
    @ResponseBody
    public Object test06(){
        TblEmployee tblEmployee = new TblEmployee();
        tblEmployee.setAge(23);
        tblEmployee.setEmail("347863282@qq.com");
        tblEmployee.setGender("1");
        tblEmployee.setLastName("Pen");
        return this.tblEmployeeServiceImpl.insByTblEmployee(tblEmployee);
    }

    /**
     * 逻辑删除下的真删除
     * @param id
     * @return
     */
    @RequestMapping("/test07/{id}")
    @ResponseBody
    public Object test07(@PathVariable Long id){
        return this.tblEmployeeServiceImpl.realDelById(id);
    }
}
