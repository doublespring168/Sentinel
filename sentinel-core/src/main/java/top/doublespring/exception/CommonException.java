package top.doublespring.exception;

public class CommonException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -6830897948792524762L;

    public CommonException(String message) {
        super(message);
    }

    public CommonException(Throwable cause) {
        super(cause);
    }
}
