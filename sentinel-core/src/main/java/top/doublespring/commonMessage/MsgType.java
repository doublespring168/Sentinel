package top.doublespring.commonMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * 错误码（枚举建议首字母大写，未大写的以后统一）
 */
public enum MsgType {

    // 错误码定义 1 - 500
    Success("1", "success"),
    Fail("2", "fail"),
    Warn("3", "警告"),
    Info("4", "提示"),
    Loginerror("5", "登录失败"),
    //400 - 500 服务器错误
    SERVER_ERROR("500", "服务器内部异常，请联系管理员。"),
    SERVER_ERROR_TIP("0", "警告"),


    //其他提示信息 501 - 1000
    NO_ID("501", "参数【id】不能为空。"),
    NO_LOGIN("502", "您没有登录系统，请登录。"),
    NO_PRIVILEGE("503", "无权限进行相关操作。"),
    NO_ARGS("504", "参数不能为空。"),
    WRONG_ARGS("505", "参数错误。"),


    SystemErr("9999", "系统未知异常"),


    //IX中间层APP WS连接专用code码
    SUCCESS("0000", "处理成功"),
    FAILED("0001", "处理失败"),
    WARNING("10001", "警告"),
    Error("10002", "错误"),

    ;
    /**
     * 静态缓存，提高效率
     */
    private static Map<String, MsgType> cache;

    static {
        cache = new HashMap<String, MsgType>();
        MsgType[] values = MsgType.values();
        for (MsgType error : values) {
            cache.put(error.code, error);
        }
    }

    /**
     * 错误编号
     */
    private String code;
    /**
     * 错误提示
     */
    private String msg;

    MsgType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * <pre>
     * 获取消息内容
     * </pre>
     *
     * @param code
     * @return
     */
    public static String getMsg(String code) {
        if (code != null && code.length() > 0) {
            MsgType msgType = cache.get(code);
            if (msgType != null) {
                return msgType.getMsg();
            }
        }

        return MsgType.SystemErr.getMsg();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}