package com.alibaba.csp.sentinel.dashboard.doublespring;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.datasource.Converter;
import org.apache.curator.framework.CuratorFramework;
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

    @Override
    public List<FlowRuleEntity> getRules(String appName) throws Exception {
        byte[] data = zkClient.getData().forPath(ZookeeperConfigUtils.getFlowRuleZkPath(appName));
        if (data == null || data.length == 0) {
            return new ArrayList<>();
        }
        return converter.convert(new String(data, StandardCharsets.UTF_8));
    }
}
