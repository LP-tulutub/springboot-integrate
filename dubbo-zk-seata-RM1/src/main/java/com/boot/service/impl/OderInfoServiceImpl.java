package com.boot.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.boot.mapper.OrderInfoMapper;
import com.boot.pojo.OrderInfo;
import com.boot.service.OrderInfoService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
@Service(interfaceClass = OrderInfoService.class)
public class OderInfoServiceImpl implements OrderInfoService {

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Transactional
    @Override
    public Integer insOrderInfo(OrderInfo orderInfo) {
        return this.orderInfoMapper.insertSelective(orderInfo);
    }
}
