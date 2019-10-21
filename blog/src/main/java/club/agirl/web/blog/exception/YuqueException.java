package club.agirl.web.blog.exception;

public class YuqueException extends WebException {
    public YuqueException() {
        super();
    }

    public YuqueException(String message) {
        super(message);
    }

    public YuqueException(String message, Throwable cause) {
        super(message, cause);
    }

    public YuqueException(Throwable cause) {
        super(cause);
    }

    protected YuqueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
