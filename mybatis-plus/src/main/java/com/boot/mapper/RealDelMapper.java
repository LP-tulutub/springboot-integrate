package com.boot.mapper;

import org.apache.ibatis.annotations.Delete;

public interface RealDelMapper {
    @Delete("delete from tbl_employee where id=#{id}")
    Integer realDelById(Long id);
}
