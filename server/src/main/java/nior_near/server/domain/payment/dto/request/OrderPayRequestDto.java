package nior_near.server.domain.payment.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderPayRequestDto {
    private Long orderId;
    private String clientName;
    private Long totalPrice;
    private String clientNumber;

    @Builder
    public OrderPayRequestDto(Long orderId, String clientName, Long totalPrice, String clientNumber) {
        this.orderId = orderId;
        this.clientName = clientName;
        this.totalPrice = totalPrice;
        this.clientNumber = clientNumber;
    }
}
