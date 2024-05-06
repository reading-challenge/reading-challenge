package kr.reading.global.exception;

public class EmailExistsException extends ApplicationException {
    public EmailExistsException() {
        super(ErrorCode.EMAIL_EXISTS);
    }
}
