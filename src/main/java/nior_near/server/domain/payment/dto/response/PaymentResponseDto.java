package nior_near.server.domain.payment.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentResponseDto {
    private String merchantUid;
    private String name;
    private Long amount;
    private String buyerEmail;
    private String buyerName;
    private String buyerTel;

    @Builder
    public PaymentResponseDto(String merchantUid, String name, Long amount, String buyerEmail, String buyerName, String buyerTel) {
        this.merchantUid = merchantUid;
        this.name = name;
        this.amount = amount;
        this.buyerEmail = buyerEmail;
        this.buyerName = buyerName;
        this.buyerTel = buyerTel;
    }
}
