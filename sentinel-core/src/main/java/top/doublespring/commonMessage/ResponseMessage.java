package top.doublespring.commonMessage;

/**
 * 接口响应，所有接口返回的JSON数据应由此类承载
 * Created by GLGGAG on 2017/6/14.
 */
public class ResponseMessage<T> extends Message {

    private static final long serialVersionUID = 1L;

    private String code;

    private String msg;

    private T data;

    public ResponseMessage(MsgType err) {
        super();
        this.code = err.getCode();
        this.msg = err.getMsg();
    }

    public ResponseMessage(MsgType err, T data) {
        this.code = err.getCode();
        this.msg = err.getMsg();
        this.data = data;
    }

    public ResponseMessage(T data) {
        this();
        this.data = data;
    }

    public ResponseMessage() {
        setRespMsg(MsgType.Success);
    }

    public void setRespMsg(MsgType msg) {
        this.code = msg.getCode();
        this.msg = msg.getMsg();
    }

    public ResponseMessage(String code, String message, T data) {
        this(code, message);
        this.data = data;
    }

    public ResponseMessage(String code, String message) {
        this.code = code;
        this.msg = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 处理成功
     *
     * @return
     */
    public boolean succeed() {
        return MsgType.Success.getCode().equals(code);
    }

    public void setRespMsg(MsgType msg, String customMsgText) {
        this.code = msg.getCode();
        this.msg = customMsgText;
    }
}