package top.doublespring.commonMessage;

/**
 * 功能说明： 接口调用响应参数
 * Author：zts
 * Date：2018/5/25 11:15
 */
public class RespMsg<D> extends Message {

    private static final long serialVersionUID = 1L;

    private String code;

    private String msg;

    private D data;


    public RespMsg(MsgType msgType) {
        this.msg = msgType.getMsg();
        this.code = msgType.getCode();
    }

    public RespMsg(MsgType msgType, String msg) {
        this.msg = msg;
        this.code = msgType.getCode();
    }


    public RespMsg(MsgType msgType, D data, String msg) {
        this.msg = msg;
        this.data = data;
        this.code = msgType.getCode();
    }

    public RespMsg() {

    }

    /**
     * 功能说明：返回成功状态
     * Author：zts
     * Date：2018/5/25 11:30
     */
    public static <D> RespMsg<D> success() {
        return new RespMsg<D>(MsgType.Success);
    }

    /**
     * 功能说明：返回成功状态、提示信息
     * Author：zts
     * Date：2018/5/25 11:30
     */
    public static <D> RespMsg<D> success(String msg) {
        return new RespMsg<D>(MsgType.Success, msg);
    }

    /**
     * 功能说明：返回成功状态、数据
     * Author：zts
     * Date：2018/5/25 11:30
     */
    public static <D> RespMsg<D> success(D data) {
        return RespMsg.success(data, null);
    }

    /**
     * 功能说明：返回成功状态、数据和提示信息
     * Author：zts
     * Date：2018/5/25 11:30
     */
    public static <D> RespMsg<D> success(D data, String msg) {
        return new RespMsg<D>(MsgType.Success, data, msg);
    }

    /**
     * 功能说明：通用返回消息类型和提示信息
     * Author：zts
     * Date：2018/5/25 11:30
     */
    public static <D> RespMsg<D> initRespMsg(MsgType msgType, String msg) {
        return new RespMsg<D>(msgType, msg);
    }

    /**
     * 功能说明：返回失败
     * Author：zts
     * Date：2018/5/25 11:30
     */
    public static <D> RespMsg<D> fail() {
        return new RespMsg<D>(MsgType.Fail);
    }

    /**
     * 功能说明：返回失败，并返回失败提示信息
     * Author：zts
     * Date：2018/5/25 11:30
     */
    public static <D> RespMsg<D> fail(String msg) {
        return new RespMsg<D>(MsgType.Fail, msg);
    }

    /**
     * 功能说明：返回成功状态、数据和提示信息
     * Author：zts
     * Date：2018/5/25 11:30
     */
    public static <D> RespMsg<D> fail(D data) {
        return new RespMsg<D>(MsgType.Fail, data, MsgType.Fail.getMsg());
    }

    /**
     * 功能说明：返回成功状态、数据和提示信息
     * Author：zts
     * Date：2018/5/25 11:30
     */
    public static <D> RespMsg<D> fail(D data, String msg) {
        return new RespMsg<D>(MsgType.Fail, data, msg);
    }

    public String getCode() {
        return code;
    }

    public RespMsg<D> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public RespMsg<D> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public D getData() {
        return data;
    }

    public RespMsg<D> setData(D data) {
        this.data = data;
        return this;
    }


}