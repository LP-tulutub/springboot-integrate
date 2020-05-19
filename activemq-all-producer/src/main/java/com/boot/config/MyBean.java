package com.boot.config;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;


import javax.jms.DeliveryMode;

@EnableJms
@Configuration
public class MyBean {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;
    @Value("${spring.activemq.user}")
    private String user;
    @Value("${spring.activemq.password}")
    private String password;
    @Value("${spring.activemq.close-timeout}")
    private String closeTimeout;
    @Value("${spring.activemq.non-blocking-redelivery}")
    private Boolean nonBlockingRedelivery;
    @Value("${spring.activemq.send-timeout}")
    private Integer sendTimeout;
    @Value("${spring.activemq.packages.trust-all}")
    private Boolean packagesTrustAll;
    @Value("${spring.activemq.pool.block-if-full}")
    private Boolean blockIfFull;
    @Value("${spring.activemq.pool.block-if-full-timeout}")
    private String blockIfFullTimeout;
    @Value("${spring.activemq.pool.idle-timeout}")
    private String idleTimeout;
    @Value("${spring.activemq.pool.max-connections}")
    private Integer maxConnections;
    @Value("${spring.activemq.pool.max-sessions-per-connection}")
    private Integer maxSessionsPerConnection;
    @Value("${spring.activemq.pool.time-between-expiration-check}")
    private String timeBetweenExpirationCheck;
    @Value("${spring.activemq.pool.use-anonymous-producers}")
    private Boolean useAnonymousProducers;


    @Bean(name = "myRedeliveryPolicy")
    public RedeliveryPolicy myRedeliveryPolicy(){
        RedeliveryPolicy  redeliveryPolicy =   new RedeliveryPolicy();

        //是否在每次尝试重新发送失败后,增长这个等待时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        //重发次数,默认为6次   这里设置为10次
        redeliveryPolicy.setMaximumRedeliveries(10);
        //重发时间间隔,默认为1秒
        redeliveryPolicy.setInitialRedeliveryDelay(1);
        //第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value
        redeliveryPolicy.setBackOffMultiplier(2);
        //是否避免消息碰撞
        redeliveryPolicy.setUseCollisionAvoidance(false);
        //设置重发最大拖延时间-1 表示没有拖延只有UseExponentialBackOff(true)为true时生效
        redeliveryPolicy.setMaximumRedeliveryDelay(-1);
        return redeliveryPolicy;
    }

    @Bean(name = "myPooledConnectionFactory")
    public PooledConnectionFactory myPooledConnectionFactory(){
        // 设置连接
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(this.user, this.password, this.brokerUrl);
        // 设置重发策略
        factory.setRedeliveryPolicy(this.myRedeliveryPolicy());
        // 设置连接等待时长
        factory.setCloseTimeout(Integer.parseInt(this.closeTimeout.substring(0, this.closeTimeout.length()-1)) * 1000);
        // 设置是否在回滚回滚消息之前停止消息传递；这意味着当启用此命令时，消息顺序不会被保留
        factory.setNonBlockingRedelivery(this.nonBlockingRedelivery);
        // 设置等待消息发送响应的时间，设置为0等待永远
        factory.setSendTimeout(this.sendTimeout);
        // 设置是否信任所有包
        factory.setTrustAllPackages(this.packagesTrustAll);

        // 创建连接池
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        // 设置 ActiveMQConnectionFactory
        pooledConnectionFactory.setConnectionFactory(factory);
        // 设置当连接请求和池满时是否阻塞，设置 false 会抛 JMSException 异常
        pooledConnectionFactory.setBlockIfSessionPoolIsFull(this.blockIfFull);
        // 设置如果池满，则在抛出异常前阻塞时间
        pooledConnectionFactory.setBlockIfSessionPoolIsFullTimeout(Long.parseLong(this.blockIfFullTimeout.substring(0, 2)));
        // 设置连接空闲超时
        pooledConnectionFactory.setIdleTimeout(Integer.parseInt(this.idleTimeout.substring(0, 2)) * 1000);
        // 设置最大连接数
        pooledConnectionFactory.setMaxConnections(this.maxConnections);
        // 设置每个连接的有效会话的最大数目
        pooledConnectionFactory.setMaximumActiveSessionPerConnection(this.maxSessionsPerConnection);
        // 设置在空闲连接清除线程之间运行的时间；当为负数时，没有空闲连接驱逐线程运行
        pooledConnectionFactory.setTimeBetweenExpirationCheckMillis(Long.parseLong(this.timeBetweenExpirationCheck.substring(0, 2)));
        // 设置是否只使用一个 MessageProducer
        pooledConnectionFactory.setUseAnonymousProducers(this.useAnonymousProducers);
        pooledConnectionFactory.start();

        return pooledConnectionFactory;
    }

    @Bean(name = "myJmsTemplate")
    public JmsTemplate myJmsTemplate(){
        JmsTemplate jmsTemplate = new JmsTemplate();

        jmsTemplate.setExplicitQosEnabled(true);
        // 设置持久化 PERSISTENT、非持久化 NON_PERSISTENT
        jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
        // 设置优先级
        jmsTemplate.setPriority(2);
        // 设置消息过期时间，0 为永远不过期
        jmsTemplate.setTimeToLive(0);
        // 设置Queue、Topic
        jmsTemplate.setPubSubDomain(false);
        // 更改为 ActiveMQConnectionFactory
        jmsTemplate.setConnectionFactory(this.myPooledConnectionFactory());

        return jmsTemplate;
    }

}
