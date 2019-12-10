package top.doublespring.log;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Supplier;

/**
 * 日志管理, 使用此 utils 获取 log, 不要在类中使用 LoggerFactory.getLogger 的方式!
 */
public final class LogUtil {

    /**
     * 根日志: 在类里面使用 LoggerFactory.getLogger(XXX.class) 跟这种方式一样!
     */
    private static final Logger ROOT_LOG = LoggerFactory.getLogger("root");


    /**
     *
     */
    public static final Logger BUSINESS_LOG = LoggerFactory.getLogger("queryDepthLogger");


    /**
     * SQL 相关的日志
     */
    public static final Logger SQL_LOG = LoggerFactory.getLogger("sqlLog");


    /**
     * 接收到请求的时间. 在 log.xml 中使用 %X{receiveTime} 获取
     */
    private static final String RECEIVE_TIME = "receiveTime";
    /**
     * 请求信息, 包括有 ip、url, param 等
     */
    private static final String REQUEST_INFO = "requestInfo";
    /**
     * 请求里包含的头信息
     */
    private static final String HEAD_INFO = "headInfo";


    /**
     * 功能说明：判断是否是debug模式
     * Author：zts
     * Date：2018/10/22 下午3:00
     */
    public static Boolean isDebug() {
        return ROOT_LOG.isDebugEnabled();
    }

    /**
     * 普通 info 日志
     * 日志格式 : 行号 , 功能编码, 类全名.方法名, 日志信息
     *
     * @param mmt
     * @param msg
     */
    public static void info(LCT mmt, String msg) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        //ROOT_LOG.info(stackTraceElement.getLineNumber() + ",[" + mmt.getCode() + "]," + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + "," + mmt.getName() + ":" + msg);
        ROOT_LOG.info("{},[{}],{}.{},{}:{}",
                stackTraceElement.getLineNumber(),
                mmt.getCode(),
                stackTraceElement.getClassName(),
                stackTraceElement.getMethodName(),
                mmt.getName(),
                msg
        );
    }

    /**
     * 普通 info 日志
     * 日志格式 : 行号 , 功能编码, 类全名.方法名, 日志信息
     *
     * @param mmt
     * @param msg
     */
    public static void info(LCT mmt, Supplier<String> msg) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        //ROOT_LOG.info(stackTraceElement.getLineNumber() + ",[" + mmt.getCode() + "]," + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + "," + mmt.getName() + ":" + msg.get());
        ROOT_LOG.info("{},[{}],{}.{},{}:{}",
                stackTraceElement.getLineNumber(),
                mmt.getCode(),
                stackTraceElement.getClassName(),
                stackTraceElement.getMethodName(),
                mmt.getName(),
                msg.get()
        );
    }

    /**
     * 普通 info 日志
     * 日志格式 : 行号 , 功能编码, 类全名.方法名, 日志信息
     *
     * @param mmt
     * @param msg
     */
    public static void info(LCT mmt, String remark, Supplier<String> msg) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        ROOT_LOG.info("{},[{}],{}.{},{},{}:{}",
                stackTraceElement.getLineNumber(),
                mmt.getCode(),
                stackTraceElement.getClassName(),
                stackTraceElement.getMethodName(),
                mmt.getName(),
                remark,
                msg.get()
        );
    }

    /**
     * 分离出来业务查看日志 info 日志
     * 日志格式 : 行号 , 功能编码, 类全名.方法名, 日志信息
     *
     * @param mmt
     * @param msg
     */
    public static void businessInfo(LCT mmt, String msg) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        ROOT_LOG.info("{},[{}],{}.{},{}:{}",
                stackTraceElement.getLineNumber(),
                mmt.getCode(),
                stackTraceElement.getClassName(),
                stackTraceElement.getMethodName(),
                mmt.getName(),
                msg
        );
    }


    /**
     * 功能说明：重载方法
     * Author：zts
     * Date：2017-12-15 11:53
     */
    public static void info(LCT mmt, Object... msgs) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        StringBuilder tmp = new StringBuilder();
        for (Object msg : msgs) {
            if (msg != null) {
                tmp.append(msg.toString()).append(",");
            }
        }
        int len = tmp.length();
        if (len > 0) {
            tmp = tmp.replace(len - 1, len, "");
        }
        ROOT_LOG.info("{},[{}],{}.{},{}:{}",
                stackTraceElement.getLineNumber(),
                mmt.getCode(),
                stackTraceElement.getClassName(),
                stackTraceElement.getMethodName(),
                mmt.getName(),
                tmp.toString()
        );
    }

    /**
     * 普通 error 日志
     * 日志格式 : 行号 , 功能编码, 类全名.方法名, 日志信息
     *
     * @param mmt
     * @param msg
     */
    public static void error(LCT mmt, String msg) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        ROOT_LOG.error("{},[{}],{}.{},{}:{}",
                stackTraceElement.getLineNumber(),
                mmt.getCode(),
                stackTraceElement.getClassName(),
                stackTraceElement.getMethodName(),
                mmt.getName(),
                msg
        );
    }


    /**
     * 功能说明：重载日志方法
     * Author：zts
     * Date：2017-12-15 11:55
     */
    public static void error(LCT mmt, String... msgs) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        StringBuilder tmp = new StringBuilder();
        for (String msg : msgs) {
            tmp.append(msg).append(",");
        }
        ROOT_LOG.error("{},[{}],{}.{},{}:{}",
                stackTraceElement.getLineNumber(),
                mmt.getCode(),
                stackTraceElement.getClassName(),
                stackTraceElement.getMethodName(),
                mmt.getName(),
                tmp.toString()
        );
    }

    /**
     * 功能说明：重载日志方法
     * Author：zts
     * Date：2017-12-15 11:55
     */
    public static void error(String... msgs) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        StringBuilder tmp = new StringBuilder();
        for (String msg : msgs) {
            tmp.append(msg).append(",");
        }
        LCT mmt = LCT.CODE_110;
        ROOT_LOG.error("{},[{}],{}.{},{}:{}",
                stackTraceElement.getLineNumber(),
                mmt.getCode(),
                stackTraceElement.getClassName(),
                stackTraceElement.getMethodName(),
                mmt.getName(),
                tmp.toString()
        );
    }


    /**
     * 普通 info 日志
     * 日志格式 : 行号 , 功能编码, 类全名.方法名, 日志信息
     *
     * @param mmt
     * @param msg
     */
    public static void error(LCT mmt, Supplier<String> msg) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        //ROOT_LOG.info(stackTraceElement.getLineNumber() + ",[" + mmt.getCode() + "]," + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + "," + mmt.getName() + ":" + msg.get());
        ROOT_LOG.error("{},[{}],{}.{},{}:{}",
                stackTraceElement.getLineNumber(),
                mmt.getCode(),
                stackTraceElement.getClassName(),
                stackTraceElement.getMethodName(),
                mmt.getName(),
                msg.get()
        );
    }

    /**
     * 普通 info 日志
     * 日志格式 : 行号 , 功能编码, 类全名.方法名, 日志信息
     *
     * @param mmt
     * @param msg
     */
    public static void error(LCT mmt, String remark, Supplier<String> msg) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        ROOT_LOG.error("{},[{}],{}.{},{},{}:{}",
                stackTraceElement.getLineNumber(),
                mmt.getCode(),
                stackTraceElement.getClassName(),
                stackTraceElement.getMethodName(),
                mmt.getName(),
                remark,
                msg.get()
        );
    }

    /**
     * 普通 info 日志
     * 日志格式 : 行号 , 功能编码, 类全名.方法名, 日志信息
     *
     * @param msg
     */
    public static void error(String remark, Supplier<String> msg) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        LCT mmt = LCT.CODE_110;
        ROOT_LOG.error("{},[{}],{}.{},{},{}:{}",
                stackTraceElement.getLineNumber(),
                mmt.getCode(),
                stackTraceElement.getClassName(),
                stackTraceElement.getMethodName(),
                mmt.getName(),
                remark,
                msg.get()
        );
    }

    /**
     * 普通 debug 日志
     * 日志格式 : 行号 , 功能编码, 类全名.方法名, 日志信息
     *
     * @param mmt
     * @param msg
     */
    public static void debug(LCT mmt, String msg) {
        if (ROOT_LOG.isDebugEnabled()) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
            ROOT_LOG.debug("{},[{}],{}.{},{}:{}",
                    stackTraceElement.getLineNumber(),
                    mmt.getCode(),
                    stackTraceElement.getClassName(),
                    stackTraceElement.getMethodName(),
                    mmt.getName(),
                    msg
            );
        }
    }


    /**
     * 普通 debug 日志
     * 日志格式 : 行号 , 功能编码, 类全名.方法名, 日志信息
     *
     * @param mmt
     * @param msg
     */
    public static void debug(LCT mmt, String remark, Supplier<String> msg) {
        if (ROOT_LOG.isDebugEnabled()) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
            ROOT_LOG.debug("{},[{}],{}.{},{},{}:{}",
                    stackTraceElement.getLineNumber(),
                    mmt.getCode(),
                    stackTraceElement.getClassName(),
                    stackTraceElement.getMethodName(),
                    mmt.getName(),
                    remark,
                    msg.get()
            );
        }
    }

    /**
     * 普通 debug 日志
     * 日志格式 : 行号 , 功能编码, 类全名.方法名, 日志信息
     *
     * @param mmt
     * @param msg
     */
    public static void debug(LCT mmt, Supplier<String> msg) {
        if (ROOT_LOG.isDebugEnabled()) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
            ROOT_LOG.debug("{},[{}],{}.{},{}:{}",
                    stackTraceElement.getLineNumber(),
                    mmt.getCode(),
                    stackTraceElement.getClassName(),
                    stackTraceElement.getMethodName(),
                    mmt.getName(),
                    msg.get()
            );
        }
    }

    /**
     * 功能说明：重载日志方法
     * Author：zts
     * Date：2017-12-15 11:56
     */
    public static void debug(LCT mmt, String... msgs) {
        if (ROOT_LOG.isDebugEnabled()) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
            StringBuilder tmp = new StringBuilder();
            for (String msg : msgs) {
                tmp.append(msg).append(",");
            }
            ROOT_LOG.debug("{},[{}],{}.{},{}:{}",
                    stackTraceElement.getLineNumber(),
                    mmt.getCode(),
                    stackTraceElement.getClassName(),
                    stackTraceElement.getMethodName(),
                    mmt.getName(),
                    tmp.toString()
            );
        }
    }

    public static class LogContextInfo {
        /**
         * 访问 ip
         */
        String ip;
        /**
         * 访问方法
         */
        String method;
        /**
         * 访问地址
         */
        String url;
        /**
         * 请求 body 中的参数
         */
        String param;
        /**
         * 请求 header 中的参数
         */
        String headParam;

        private String paramInfo() {
            return String.format("%s (%s %s) param(%s)", ip, method, url, param);
        }

        private String headParamInfo() {
            return String.format("header(%s)", headParam);
        }

        public String getIp() {
            return ip;
        }

        public LogContextInfo setIp(String ip) {
            this.ip = ip;
            return this;
        }

        public String getMethod() {
            return method;
        }

        public LogContextInfo setMethod(String method) {
            this.method = method;
            return this;
        }

        public String getUrl() {
            return url;
        }

        public LogContextInfo setUrl(String url) {
            this.url = url;
            return this;
        }

        public String getParam() {
            return param;
        }

        public LogContextInfo setParam(String param) {
            this.param = param;
            return this;
        }

        public String getHeadParam() {
            return headParam;
        }

        public LogContextInfo setHeadParam(String headParam) {
            this.headParam = headParam;
            return this;
        }
    }
}
