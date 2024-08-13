package nior_near.server.domain.payment.api;

import com.siot.IamportRestClient.response.IamportResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.payment.application.PaymentService;
import nior_near.server.domain.payment.dto.request.RequestPayDto;
import nior_near.server.domain.payment.dto.request.PaymentCallbackRequest;
import nior_near.server.domain.payment.entity.Payment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/payment/{id}")
    public String paymentPage(@PathVariable(name = "id", required = false) String Uid, Model model) {
        RequestPayDto requestDto = paymentService.findRequestDto(Uid);
        model.addAttribute("requestDto", requestDto);
        return "payment";
    }

    @ResponseBody
    @PostMapping("/payment")
    public ResponseEntity<IamportResponse<com.siot.IamportRestClient.response.Payment>> validationPayment(@RequestBody PaymentCallbackRequest request) {
        IamportResponse<com.siot.IamportRestClient.response.Payment> iamportResponse = paymentService.paymentByCallBack(request);

        log.info("결제 응답={}", iamportResponse.getResponse().toString());

        return new ResponseEntity<>(iamportResponse, HttpStatus.OK);
    }

    @GetMapping("/success-payment")
    public String successPaymentPage() {
        return "success-payment";
    }

    @GetMapping("/fail-payment")
    public String failPaymentPage() {
        return "fail-payment";
    }
}
