package nior_near.server.domain.order.application;

import lombok.Builder;
import lombok.Getter;
import nior_near.server.domain.order.entity.OrderMenu;

@Builder
@Getter
public class MyOrderMenuResponseDto {

    private Integer menuQuantity;
    private String menuName;

    public static MyOrderMenuResponseDto of(OrderMenu orderMenu) {

        return MyOrderMenuResponseDto.builder()
                .menuQuantity(orderMenu.getQuantity())
                .menuName(orderMenu.getMenu().getName())
                .build();
    }
}
