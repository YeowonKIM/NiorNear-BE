package nior_near.server.domain.order.exception.handler;

import nior_near.server.global.common.ResponseCode;
import nior_near.server.global.error.GeneralException;

public class OrderHandler extends GeneralException {

    public OrderHandler(ResponseCode errorCode) {
        super(errorCode);
    }

}
