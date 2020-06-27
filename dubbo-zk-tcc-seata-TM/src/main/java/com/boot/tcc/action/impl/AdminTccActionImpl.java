package com.boot.tcc.action.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.boot.pojo.Admin;
import com.boot.service.AdminService;
import com.boot.tcc.action.AdminTccAction;
import com.boot.utils.SpringContext;
import com.boot.utils.JsonUtils;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
@Component
@Service(interfaceClass = AdminTccAction.class)
public class AdminTccActionImpl implements AdminTccAction {

    @Autowired
    private TransactionTemplate fromDsTransactionTemplate;

    /**
     * 插入数据，数据为不可视状态
     * @param businessActionContext
     * @param admin
     * @param amount
     * @return
     */
    @Override
    public boolean prepare(BusinessActionContext businessActionContext, String admin, double amount) {

        // 分布式事务ID
        final String xid = businessActionContext.getXid();

        Boolean result = fromDsTransactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                try {
                    log.warn("prepare 准备阶段开始: xid {}", xid);
                    AdminService adminServiceImpl =(AdminService) SpringContext.getContext().getBean("adminServiceImpl");
                    Integer insAdmin = adminServiceImpl.insAdmin(JsonUtils.jsonToPojo(admin, Admin.class));
                    if (insAdmin != 1) throw new RuntimeException("commit 阶段: 新增 Admin 失败");
                    //if (true) throw new RuntimeException("seata TCC 模式分布式事务测试");
                    log.warn("prepare 准备阶段完成");
                    return true;
                } catch (Throwable t) {
                    t.printStackTrace();
                    status.setRollbackOnly();
                    return false;
                }
            }
        });
        if (result == null) return false;
        return result;
    }

    /**
     * 修改状态为可视
     * @param businessActionContext
     * @return
     */
    @Override
    public boolean commit(BusinessActionContext businessActionContext) {
        // 分布式事务ID
        final String xid = businessActionContext.getXid();
        // 要插入的数据
        Object obj = businessActionContext.getActionContext("admin");

        Boolean result = fromDsTransactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                try {
                    log.warn("commit 阶段开始: xid {}", xid);
                    AdminService adminServiceImpl =(AdminService) SpringContext.getContext().getBean("adminServiceImpl");
                    if (obj == null) throw new RuntimeException("commit 阶段: admin 对象为空");
                    // 开始修改
                    Admin admin = JsonUtils.jsonToPojo((String) obj, Admin.class);
                    admin.setName(null);
                    admin.setPassword(null);
                    admin.setSlot("1");
                    Integer updAdmin = adminServiceImpl.updAdminById(admin);
                    if (updAdmin != 1) throw new RuntimeException("commit 阶段: 修改 Admin 失败");
                    log.warn("commit 阶段完成");
                    return true;
                } catch (Throwable t) {
                    t.printStackTrace();
                    status.setRollbackOnly();
                    return false;
                }
            }
        });
        if (result == null) return false;
        return result;
    }

    /**
     * 删除插入的数据，如果 prepare 方法出错不用手动回滚(任何位置出错)，如果 prepare 方法成功，需要手写回滚方法
     * @param businessActionContext
     * @return
     */
    @Override
    public boolean rollback(BusinessActionContext businessActionContext) {
        // 分布式事务ID
        final String xid = businessActionContext.getXid();
        // 要插入的数据
        Object obj = businessActionContext.getActionContext("admin");

        Boolean result = fromDsTransactionTemplate.execute(new TransactionCallback<Boolean>() {

            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                try {
                    log.warn("rollback 阶段开始回滚: xid {}", xid);
                    if (obj == null) return true;
                    AdminService adminServiceImpl =(AdminService) SpringContext.getContext().getBean("adminServiceImpl");
                    log.warn("rollback 阶段回滚数据");
                    Admin admin = JsonUtils.jsonToPojo((String) obj, Admin.class);
                    Integer delVal = adminServiceImpl.delAdminByIdExist(admin);
                    if (delVal != 1) throw new RuntimeException("rollback 回滚数据失败");
                    log.warn("rollback 阶段回滚数据成功");
                    return true;
                } catch (Throwable t) {
                    t.printStackTrace();
                    status.setRollbackOnly();
                    return false;
                }

            }
        });
        if (result == null) return false;
        return result;
    }

}
