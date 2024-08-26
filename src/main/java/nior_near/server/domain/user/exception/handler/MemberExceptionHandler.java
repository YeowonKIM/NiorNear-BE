package nior_near.server.domain.user.exception.handler;

import nior_near.server.global.common.ResponseCode;
import nior_near.server.global.error.GeneralException;

public class MemberExceptionHandler extends GeneralException {
    public MemberExceptionHandler(ResponseCode responseCode) {
        super(responseCode);
    }
}
