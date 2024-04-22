package kr.reading.global.exception;

public class UserIdExistsException extends ApplicationException {
    public UserIdExistsException() {
        super(ErrorCode.USERID_EXISTS);
    }
}
