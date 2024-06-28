package kr.reading.global.exception;

public class ChallengeNotFoundException extends ApplicationException {

    public ChallengeNotFoundException() {
        super(ErrorCode.CHALLENGE_NOT_FOUND);
    }

}
