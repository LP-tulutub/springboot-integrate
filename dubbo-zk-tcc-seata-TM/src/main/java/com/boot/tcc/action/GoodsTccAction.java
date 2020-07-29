package com.boot.tcc.action;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * TCC参与者
 */
@LocalTCC
public interface GoodsTccAction {

	 /**
     * 一阶段方法
     * 
     * @param businessActionContext
     * @param goods
     * @param amount
     */
    @TwoPhaseBusinessAction(name = "GoodsTccAction", commitMethod = "commit", rollbackMethod = "rollback")
    public boolean prepare(BusinessActionContext businessActionContext,
                              @BusinessActionContextParameter(paramName = "goods") String goods,
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
