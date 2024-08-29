package nior_near.server.domain.order.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatus {
    CONFIRM("CONFIRM"),
    COOKING("COOKING"),
    PICKUP("PICKUP"),
    FINISH("FINISH");

    private final String status;
}
