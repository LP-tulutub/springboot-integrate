package com.boot.start.config;

import com.alibaba.fastjson.JSONObject;
import com.boot.start.pojo.MyUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.springframework.stereotype.Component;


@Slf4j
//@Component
//@RocketMQTransactionListener(corePoolSize = 5, maximumPoolSize = 10)
public class MySyncTransactionListener2 implements TransactionListener {

    //private ConcurrentHashMap<String, Object> localTrans = new ConcurrentHashMap<>();

    @Override
    public LocalTransactionState executeLocalTransaction(org.apache.rocketmq.common.message.Message message, Object o) {
        try {
            log.warn("【本地开始业务执行】 msg:{}, Object:{}", message, o);
            MyUser myUser = JSONObject.parseObject((byte[]) message.getBody(), MyUser.class);
            if (myUser.getId() == 2) return LocalTransactionState.UNKNOW;
            //if (o == null || o.equals(0)) return RocketMQLocalTransactionState.UNKNOWN;
            //if (true) throw new RuntimeException("事务测试");
            return LocalTransactionState.COMMIT_MESSAGE;
        }catch (Exception e){
            log.error("【执行本地业务异常】 exception message:{}", e.getMessage());
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        log.warn("【执行检查任务】" + messageExt);
        MyUser myUser = JSONObject.parseObject((byte[]) messageExt.getBody(), MyUser.class);
        if (myUser.getId() == 2) return LocalTransactionState.COMMIT_MESSAGE;
        return LocalTransactionState.UNKNOW;
    }
}
