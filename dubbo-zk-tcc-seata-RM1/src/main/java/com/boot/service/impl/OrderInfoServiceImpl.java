package com.boot.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.boot.mapper.OderInfoMapper;
import com.boot.pojo.OrderInfo;
import com.boot.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Service(interfaceClass = OrderInfoService.class)
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OderInfoMapper oderInfoMapper;

    @Transactional
    @Override
    public Integer insOrderInfo(OrderInfo orderInfo) {
        return this.oderInfoMapper.insert(orderInfo);
    }

    @Transactional
    @Override
    public Integer updOderInfoById(OrderInfo orderInfo) {
        return this.oderInfoMapper.updateById(orderInfo);
    }

    @Transactional
    @Override
    public Integer delOderInfoByIdExist(OrderInfo orderInfo) {

        OrderInfo exist = this.oderInfoMapper.selectById(orderInfo.getId());
        if (exist == null) return 1;

        return this.oderInfoMapper.deleteById(orderInfo.getId());
    }
}
