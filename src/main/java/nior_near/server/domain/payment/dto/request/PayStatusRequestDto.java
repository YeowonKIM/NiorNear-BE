package nior_near.server.domain.payment.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class PayStatusRequestDto {
    private Long orderId;
    private String status;
}
