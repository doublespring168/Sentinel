package top.doublespring.exception;

/**
 * CreateTime: 2019-08-01 22:31
 * ClassName: MyException
 * Package: com.ix.common.exception
 * Describe:
 * 自定义异常，可以throws的时候用自己的异常类
 *
 * @author ix
 */
public class BizException extends RuntimeException {

    public BizException(String msg) {
        super(msg);
    }

    public BizException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public BizException(Throwable throwable) {
        super(throwable);
    }

}
