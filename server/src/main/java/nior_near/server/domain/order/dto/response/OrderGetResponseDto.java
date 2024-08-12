package nior_near.server.domain.order.dto.response;

import lombok.Builder;
import lombok.Getter;
import nior_near.server.domain.order.entity.OrderMenu;
import nior_near.server.domain.order.entity.OrderStatus;

import java.util.List;

@Getter
@Builder
public class OrderGetResponseDto {

    private OrderStatus orderStatus;
    private String placeAddress;
    private String storePhone;
    private String requestMessage;
    private Long totalPrice;
    private List<OrderMenuItem> orderMenus;

    @Getter
    public static class OrderMenuItem {
        private String menuName;
        private Long menuPrice;
        private Integer quantity;

        public OrderMenuItem(OrderMenu orderMenu) {
            this.menuName = orderMenu.getMenu().getName();
            this.menuPrice = orderMenu.getMenu().getPrice();
            this.quantity = orderMenu.getQuantity();
        }
    }
}
