package com.alibaba.csp.sentinel.dashboard.doublespring;

public final class ZookeeperConfigUtils {

    public static final String GROUP_ID = "ix-sentinel";
    private static final String ZK_PATH_SEPARATOR = "/";
    private static final String FLOW_RULE_DATA_ID_POSTFIX = "-flow-rules";
    private static final String AUTHORITY_RULE_DATA_ID_POSTFIX = "-authority-rules";

    private ZookeeperConfigUtils() {
    }

    /**
     * /groupId/dataId
     *
     * @param app app name
     * @return zk path
     */
    public static String getAuthorityRuleZkPath(String app) {
        return ZK_PATH_SEPARATOR + GROUP_ID + ZK_PATH_SEPARATOR + app + AUTHORITY_RULE_DATA_ID_POSTFIX;
    }

    /**
     * /groupId/dataId
     *
     * @param app app name
     * @return zk path
     */
    public static String getFlowRuleZkPath(String app) {
        return ZK_PATH_SEPARATOR + GROUP_ID + ZK_PATH_SEPARATOR + app + FLOW_RULE_DATA_ID_POSTFIX;
    }
}
