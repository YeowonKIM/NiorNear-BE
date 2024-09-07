package nior_near.server.domain.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderAddRequestDto {

    @NotNull
    private Long storeId;
    @NotNull
    private String requestMessage;
    @NotBlank
    private String memberName;
    @NotBlank
    private String memberPhone;
    @NotNull
    private List<OrderMenuItem> menus;

    @Getter @Setter
    public static class OrderMenuItem {
        @NotBlank
        private Long menuId;
        @NotBlank
        private Integer quantity;
    }
}
