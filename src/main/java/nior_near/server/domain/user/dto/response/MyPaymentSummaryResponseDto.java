package nior_near.server.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import nior_near.server.domain.order.application.MyOrderMenuResponseDto;
import nior_near.server.domain.order.entity.Order;
import nior_near.server.domain.order.entity.OrderMenu;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class MyPaymentSummaryResponseDto {

    private Long storeId;
    private String storeName;
    private String storeProfileImage;

    private Long orderId;
    private Long totalPrice;
    private List<MyOrderMenuResponseDto> orderMenuList;


    public static MyPaymentSummaryResponseDto of(Order order) {

        List<OrderMenu> orderMenus = order.getOrderMenuList();
        List<MyOrderMenuResponseDto> myOrderMenuResponseDtos = orderMenus.stream()
                        .map((orderMenu) -> MyOrderMenuResponseDto.of(orderMenu))
                        .collect(Collectors.toList());

        return MyPaymentSummaryResponseDto.builder()
                .storeId(order.getStore().getId())
                .storeName(order.getStore().getName())
                .storeProfileImage(order.getStore().getProfileImage())
                .orderId(order.getId())
                .totalPrice(order.getTotalPrice())
                .orderMenuList(myOrderMenuResponseDtos)
                .build();
    }
}
