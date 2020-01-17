package cn.knowimage.exception;

public class NullException extends BaseException {
    public NullException() {
        super();
    }

    public NullException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullException(String message) {
        super(message);
    }

    public NullException(Throwable cause) {
        super(cause);
    }
}
