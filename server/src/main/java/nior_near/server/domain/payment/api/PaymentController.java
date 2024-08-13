package nior_near.server.domain.payment.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.payment.application.PaymentService;
import nior_near.server.domain.payment.dto.response.OrderPayResponseDto;
import nior_near.server.global.common.BaseResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/payment/{orderId}")
    public BaseResponseDto<OrderPayResponseDto> getPaymentInfo(@PathVariable("orderId") Long id) {
        return paymentService.findRequestDto(id);
    }
}
