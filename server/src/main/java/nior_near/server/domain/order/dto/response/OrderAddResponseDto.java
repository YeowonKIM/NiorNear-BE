package nior_near.server.domain.order.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
public class OrderAddResponseDto {
    private Long orderId;
    private String profileImage;
    private String message;
    private long totalPrice;
    private List<OrderMenuInfo> orderMenus;

    @Getter
    public static class OrderMenuInfo {
        private String menuName;
        private long menuPrice;
        private int quantity;

        @Builder
        public OrderMenuInfo(String menuName, long menuPrice, int quantity) {
            this.menuName = menuName;
            this.menuPrice = menuPrice;
            this.quantity = quantity;
        }
    }
}
