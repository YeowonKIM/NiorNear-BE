package nior_near.server.domain.payment.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RequestPayDto {
    private String orderUid;
    private String clientName;
    private Long totalPrice;
    private String clientNumber;

    @Builder
    public RequestPayDto(String orderUid, String clientName, Long totalPrice, String clientNumber) {
        this.orderUid = orderUid;
        this.clientName = clientName;
        this.totalPrice = totalPrice;
        this.clientNumber = clientNumber;
    }
}
