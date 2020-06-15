package com.boot.start.config;

import com.alibaba.fastjson.JSONObject;
import com.boot.start.pojo.MyUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * 法一：
 * 1. 传入多个对象 + 对象的数量
 * 2. 全部进入回查 + 保存发送 half 消息的数量
 * 3. 回查发现对象数量 = 发送 half 消息的数量 → commit(否则 rollback)
 * 法二：
 * 1. 传入就提交 + 保存提交的标记
 * 2. 如果久未提交回查 + 查询没有提交的标记信息 → commit(否则 rollback)
 */
@Slf4j
@Component
@RocketMQTransactionListener(corePoolSize = 5, maximumPoolSize = 10)
public class MySyncTransactionListener implements RocketMQLocalTransactionListener {

    //private ConcurrentHashMap<String, Object> localTrans = new ConcurrentHashMap<>();

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            log.warn("【本地开始业务执行】 msg:{}, Object:{}", message, o);
            MyUser myUser = JSONObject.parseObject((byte[]) message.getPayload(), MyUser.class);
            if (myUser.getId() == 2) throw new RuntimeException("事务测试");
            if (myUser.getId() == 3) return RocketMQLocalTransactionState.UNKNOWN; // 调用 checkLocalTransaction 方法
            //if (o == null || o.equals(0)) return RocketMQLocalTransactionState.UNKNOWN;
        return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("【执行本地业务异常】 exception message:{}", e.getMessage());

            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {

        log.warn("【执行检查任务】" + message);
        MyUser myUser = JSONObject.parseObject((byte[]) message.getPayload(), MyUser.class);
        if (myUser.getId() == 3) return RocketMQLocalTransactionState.COMMIT;

        return RocketMQLocalTransactionState.UNKNOWN; // 调用 checkLocalTransaction 方法
    }
}
