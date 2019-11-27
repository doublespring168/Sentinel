package com.alibaba.csp.sentinel.dashboard.doublespring;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.fastjson.JSON;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConditionalOnProperty(value = "dynamic.rules.source.type", havingValue = "zookeeper")
@Configuration
@EnableConfigurationProperties(ZookeeperConfigProperties.class)
public class ZookeeperConfig {


    private final Logger logger = LoggerFactory.getLogger(ZookeeperConfig.class);

    private static final int DEFAULT_ZK_SESSION_TIMEOUT = 30000;
    private static final int DEFAULT_ZK_CONNECTION_TIMEOUT = 10000;
    private static final int RETRY_TIMES = 3;
    private static final int SLEEP_TIME = 1000;

    public ZookeeperConfig() {
        logger.info("============== Use Zookeeper Dynamic Rules Source ===================");
    }

    /**
     * flow rule entity encoder
     *
     * @return Converter<List       <       FlowRuleEntity>, String>
     */
    @Bean
    public Converter<List<FlowRuleEntity>, String> flowRuleEntityEncoder() {
        return JSON::toJSONString;
    }

    /**
     * flow rule entity decoder
     *
     * @return Converter<String   ,       List       <       FlowRuleEntity>>
     */
    @Bean
    public Converter<String, List<FlowRuleEntity>> flowRuleEntityDecoder() {
        return s -> JSON.parseArray(s, FlowRuleEntity.class);
    }


    /**
     * authority rule entity encoder
     *
     * @return Converter<List       <       AuthorityRuleEntity>, String>
     */
    @Bean
    public Converter<List<AuthorityRuleEntity>, String> authorityRuleEntityEncoder() {
        return JSON::toJSONString;
    }


    /**
     * authority rule entity decoder
     *
     * @return Converter<String   ,       List       <       AuthorityRuleEntity>>
     */
    @Bean
    public Converter<String, List<AuthorityRuleEntity>> authorityRuleEntityDecoder() {
        return s -> JSON.parseArray(s, AuthorityRuleEntity.class);
    }


    /**
     * zk client
     *
     * @param properties zk properties
     * @return zk client
     */
    @Bean(destroyMethod = "close")
    public CuratorFramework zkClient(ZookeeperConfigProperties properties) {
        String connectString = properties.getConnectString();
        int sessionTimeout = DEFAULT_ZK_SESSION_TIMEOUT;
        int connectionTimeout = DEFAULT_ZK_CONNECTION_TIMEOUT;
        if (properties.getSessionTimeout() > 0) {
            sessionTimeout = properties.getSessionTimeout();
        }
        if (properties.getConnectionTimeout() > 0) {
            connectionTimeout = properties.getConnectionTimeout();
        }

        CuratorFramework zkClient = CuratorFrameworkFactory.newClient(connectString,
                sessionTimeout, connectionTimeout,
                new ExponentialBackoffRetry(SLEEP_TIME, RETRY_TIMES));
        zkClient.start();

        logger.info("Initialize zk client CuratorFramework, connectString={}, sessionTimeout={}, connectionTimeout={}, retry=[sleepTime={}, retryTime={}]",
                connectString, sessionTimeout, connectionTimeout, SLEEP_TIME, RETRY_TIMES);
        return zkClient;
    }

    @Bean
    public FlowRuleZookeeperProvider flowRuleZookeeperProvider() {
        return new FlowRuleZookeeperProvider();
    }

    @Bean
    public FlowRuleZookeeperPublisher flowRuleZookeeperPublisher() {
        return new FlowRuleZookeeperPublisher();
    }
}
