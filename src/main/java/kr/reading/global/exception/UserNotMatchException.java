package kr.reading.global.exception;

public class UserNotMatchException extends ApplicationException {

    public UserNotMatchException() {
        super(ErrorCode.USER_NOT_MATCH);
    }

}
