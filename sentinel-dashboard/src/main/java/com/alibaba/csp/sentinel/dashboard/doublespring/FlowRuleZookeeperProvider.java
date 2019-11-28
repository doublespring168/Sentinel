package com.alibaba.csp.sentinel.dashboard.doublespring;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.datasource.Converter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.ExistsBuilder;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("flowRuleZookeeperProvider")
public class FlowRuleZookeeperProvider implements DynamicRuleProvider<List<FlowRuleEntity>> {

    @Autowired
    private CuratorFramework zkClient;

    @Autowired
    private Converter<String, List<FlowRuleEntity>> converter;

    private final Logger logger = LoggerFactory.getLogger(FlowRuleZookeeperProvider.class);


    @Override
    public List<FlowRuleEntity> getRules(String appName) throws Exception {
        String flowRuleZkPath = ZookeeperConfigUtils.getFlowRuleZkPath(appName);
        ExistsBuilder existsBuilder = zkClient.checkExists();
        Stat stat = existsBuilder.forPath(flowRuleZkPath);
        if (stat == null) {
            logger.info(String.format("即将创建节点[%s]", flowRuleZkPath));
            zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(flowRuleZkPath, "".getBytes());
        }

        byte[] data = zkClient.getData().forPath(flowRuleZkPath);
        if (data == null || data.length == 0) {
            return new ArrayList<>();
        }
        return converter.convert(new String(data, StandardCharsets.UTF_8));
    }
}
