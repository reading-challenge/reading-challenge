package kr.reading.global.exception;

public class NicNameExistsException extends ApplicationException {
    public NicNameExistsException() {
        super(ErrorCode.NICKNAME_EXISTS);
    }
}
