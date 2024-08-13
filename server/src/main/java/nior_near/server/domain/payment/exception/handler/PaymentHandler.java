package nior_near.server.domain.payment.exception.handler;

import nior_near.server.global.common.ResponseCode;
import nior_near.server.global.error.GeneralException;

public class PaymentHandler extends GeneralException {

    public PaymentHandler(ResponseCode errorCode) {
        super(errorCode);
    }
}
