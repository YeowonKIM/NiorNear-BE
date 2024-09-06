package nior_near.server.domain.payment.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PayStatusResponseDto {
    private Long orderId;
}
