package com.boot.tcc.action;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * TCC参与者
 */
@LocalTCC
public interface OrderInfoTccAction {

	 /**
     * 一阶段方法
     * 
     * @param businessActionContext
     * @param orderInfo
     * @param amount
     */
    @TwoPhaseBusinessAction(name = "OrderInfoTccAction", commitMethod = "commit", rollbackMethod = "rollback")
    public boolean prepare(BusinessActionContext businessActionContext,
                              @BusinessActionContextParameter(paramName = "orderInfo") String orderInfo,
                              @BusinessActionContextParameter(paramName = "amount") double amount);

    /**
     * 二阶段提交
     * @param businessActionContext
     * @return
     */
    public boolean commit(BusinessActionContext businessActionContext);

    /**
     * 二阶段回滚
     * @param businessActionContext
     * @return
     */
    public boolean rollback(BusinessActionContext businessActionContext);

}
