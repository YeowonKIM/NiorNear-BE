package nior_near.server.domain.letter.exception.handler;

import nior_near.server.global.common.ResponseCode;
import nior_near.server.global.error.GeneralException;

public class LetterExceptionHandler extends GeneralException {
    public LetterExceptionHandler(ResponseCode responseCode) { super(responseCode); }
}
