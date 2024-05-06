package kr.reading.global.exception;

public class UserNotFountException extends ApplicationException{
    public UserNotFountException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
