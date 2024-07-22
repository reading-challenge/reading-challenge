package kr.reading.global.exception;

public class FileSavedException extends ApplicationException{

    public FileSavedException() {
        super(ErrorCode.FILE_SAVE_ERROR);
    }

}
