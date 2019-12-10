package top.doublespring.log;

import java.io.Serializable;

public enum LCT implements Serializable {

    /**
     * 功能枚举类
     * 号段使用者分类
     * 900 - 1000 : 非业务类型日志
     */
    CODE_110("110", "异常错误"),
    CODE_100("100", "测试消息"),


    CODE_1001("1001", "客户端注册服务"),


    ;

    /**
     * 功能编码
     */
    private String code;


    /**
     * 功能名称
     */
    private String name;

    LCT(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public LCT setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public LCT setName(String name) {
        this.name = name;
        return this;
    }
}
