package com.boot.tcc.service.impl;

import com.boot.tcc.action.AdminTccAction;
import com.boot.tcc.service.AdminTransferService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminTransferServiceImpl implements AdminTransferService {

    @Autowired
    private AdminTccAction adminTccActionImpl;

    @GlobalTransactional(name = "TransferByAdminEventService-transfer", timeoutMills = 60000, rollbackFor = Exception.class)
    @Override
    public boolean transfer(String admin) {

        boolean prepare = adminTccActionImpl.prepare(null, admin, 0);
        if (!prepare){
            throw new RuntimeException("admin 准备阶段错误");
        }
        return true;
    }
}
