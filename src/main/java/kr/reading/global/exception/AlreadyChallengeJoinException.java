package kr.reading.global.exception;

public class AlreadyChallengeJoinException extends ApplicationException{
    public AlreadyChallengeJoinException() {
        super(ErrorCode.CHALLENGE_JOIN);
    }
}
