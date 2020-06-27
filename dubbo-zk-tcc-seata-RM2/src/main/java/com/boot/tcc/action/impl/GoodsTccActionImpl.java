package com.boot.tcc.action.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.boot.pojo.Goods;
import com.boot.service.GoodsService;
import com.boot.tcc.action.GoodsTccAction;
import com.boot.utils.JsonUtils;
import com.boot.utils.SpringContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
@Component
@Service(interfaceClass = GoodsTccAction.class)
public class GoodsTccActionImpl implements GoodsTccAction {

    @Autowired
    private TransactionTemplate fromDsTransactionTemplate;

    /**
     * 插入数据，数据为不可视状态，不可视状态的数据不会给前端
     * @param businessActionContext
     * @param goods
     * @param amount
     * @return
     */
    @Override
    public boolean prepare(BusinessActionContext businessActionContext, String goods, double amount) {

        // 分布式事务ID
        final String xid = businessActionContext.getXid();

        Boolean result = fromDsTransactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                try {
                    log.warn("prepare 准备阶段开始: xid {}", xid);
                    GoodsService goodsServiceImpl =(GoodsService) SpringContext.getContext().getBean("goodsServiceImpl");
                    Integer insVal = goodsServiceImpl.insGoods(JsonUtils.jsonToPojo(goods, Goods.class));
                    if (insVal != 1) throw new RuntimeException("commit 阶段: 新增失败");
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
        Object obj = businessActionContext.getActionContext("goods");

        Boolean result = fromDsTransactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                try {
                    log.warn("commit 阶段开始: xid {}", xid);
                    if (obj == null) throw new RuntimeException("commit 阶段: 对象为空");
                    GoodsService goodsServiceImpl =(GoodsService) SpringContext.getContext().getBean("goodsServiceImpl");
                    // 开始修改
                    Goods goods = JsonUtils.jsonToPojo((String) obj, Goods.class);
                    Goods updGoods = new Goods();
                    updGoods.setId(goods.getId());
                    updGoods.setSlot("1");
                    Integer updVal = goodsServiceImpl.updGoodsById(updGoods);
                    if (updVal != 1) throw new RuntimeException("commit 阶段: 修改失败");
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
        Object obj = businessActionContext.getActionContext("goods");

        Boolean result = fromDsTransactionTemplate.execute(new TransactionCallback<Boolean>() {

            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                try {
                    log.warn("rollback 阶段开始回滚: xid {}", xid);
                    if (obj == null) return true;
                    // 开始回滚
                    GoodsService goodsServiceImpl =(GoodsService) SpringContext.getContext().getBean("goodsServiceImpl");
                    Goods goods = JsonUtils.jsonToPojo((String) obj, Goods.class);
                    log.warn("rollback 阶段回滚数据");
                    Integer delVal = goodsServiceImpl.delGoodsByIdExist(goods);
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
