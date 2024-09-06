package nior_near.server.domain.order.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderAddRequestDto {
    private Long storeId;
    private String requestMessage;
    private String memberName;
    private String memberPhone;
    private List<OrderMenuItem> menus;

    @Getter @Setter
    public static class OrderMenuItem {
        private Long menuId;
        private Integer quantity;
    }
}
