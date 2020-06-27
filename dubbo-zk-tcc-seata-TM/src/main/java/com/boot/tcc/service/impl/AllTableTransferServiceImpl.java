package com.boot.tcc.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.boot.tcc.action.AdminTccAction;
import com.boot.tcc.action.GoodsTccAction;
import com.boot.tcc.action.OrderInfoTccAction;
import com.boot.tcc.service.AllTableTransferService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllTableTransferServiceImpl implements AllTableTransferService {

    @Autowired
    private AdminTccAction adminTccActionImpl;
    @Reference
    private OrderInfoTccAction orderInfoTccActionImpl;
    @Reference
    private GoodsTccAction goodsTccActionImpl;


    @GlobalTransactional(name = "AllTableTransferServiceImpl-transfer", timeoutMills = 60000, rollbackFor = Exception.class)
    @Override
    public boolean transfer(String admin, String orderInfo, String goods) {

        boolean prepareAdmin = adminTccActionImpl.prepare(null, admin, 0);
        if (!prepareAdmin){
            throw new RuntimeException("admin 错误");
        }

        boolean prepareOrderInfo = orderInfoTccActionImpl.prepare(null, orderInfo, 0);
        if (!prepareOrderInfo){
            throw new RuntimeException("orderInfo 错误");
        }

        boolean prepareGoods = goodsTccActionImpl.prepare(null, goods, 0);
        if (!prepareGoods){
            throw new RuntimeException("goods 错误");
        }

        //if (true) throw new RuntimeException("seata TCC 分布式事务测试");

        return true;
    }
}
