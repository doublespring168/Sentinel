package top.doublespring.exception;


import top.doublespring.commonMessage.MsgType;

/**
 * 业务异常
 */
public class ServiceException extends RuntimeException {

    private String code;

    private String desc;

    public ServiceException() {
        super("业务异常");
        this.code = MsgType.Fail.getCode();
        this.desc = "业务异常";
    }

    public ServiceException(String desc) {
        super(desc);
        this.code = MsgType.Fail.getCode();
        this.desc = desc;
    }

    public ServiceException(String code, String desc) {
        super(desc);
        this.code = code;
        this.desc = desc;
    }

    public ServiceException(MsgType msgType, String desc) {
        super(desc);
        this.code = msgType.getCode();
        this.desc = desc;
    }

    public ServiceException(MsgType msgType) {
        super(msgType.getMsg());
        this.code = msgType.getCode();
        this.desc = msgType.getMsg();
    }


    // ------GET SET--------
    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
