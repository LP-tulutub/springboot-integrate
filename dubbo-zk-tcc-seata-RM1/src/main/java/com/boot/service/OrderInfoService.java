package com.boot.service;

import com.boot.pojo.OrderInfo;

public interface OrderInfoService {

    Integer insOrderInfo(OrderInfo orderInfo);

    Integer updOderInfoById(OrderInfo orderInfo);

    Integer delOderInfoByIdExist(OrderInfo orderInfo);

}
