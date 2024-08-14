package nior_near.server.domain.payment.application;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import nior_near.server.domain.order.entity.Order;
import nior_near.server.domain.order.repository.OrderRepository;
import nior_near.server.domain.payment.dto.request.RequestPayDto;
import nior_near.server.domain.payment.dto.request.PaymentCallbackRequest;
import nior_near.server.domain.payment.dto.response.PaymentResponseDto;
import nior_near.server.domain.payment.entity.PaymentStatus;
import nior_near.server.domain.payment.exception.handler.PaymentHandler;
import nior_near.server.domain.payment.repository.PaymentRepository;
import nior_near.server.domain.user.entity.Member;
import nior_near.server.global.common.BaseResponseDto;
import nior_near.server.global.common.ResponseCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final IamportClient iamportClient;

    @Override
    public RequestPayDto findRequestDto(String orderUid) {
        Order order = orderRepository.findOrderAndPaymentAndMember(orderUid)
                .orElseThrow(() -> new PaymentHandler(ResponseCode.ORDER_NOT_FOUND));

        return RequestPayDto.builder()
                .orderUid(order.getOrderUID())
                .itemName(orderUid)
                .buyerName(order.getMember().getName())
                .paymentPrice(order.getTotalPrice())
                .buyerEmail(order.getMember().getEmail())
                .buyerPhone(order.getPhone())
                .build();

    }

    @Override
    public IamportResponse<Payment> paymentByCallBack(PaymentCallbackRequest request) {
        try {
            // 결제조회
            IamportResponse<Payment> iamportResponse = iamportClient.paymentByImpUid(request.getPaymentUid());
            // 주문 내역 조회
            Order order = orderRepository.findOrderAndPayment(request.getPaymentUid())
                    .orElseThrow(() -> new PaymentHandler(ResponseCode.PAYMENT_NOT_FOUND));

            // 결제 완료가 아니면
            if (!iamportResponse.getResponse().getStatus().equals("paid")) {
                // 주문, 결제 내역 삭제
                orderRepository.delete(order);
                paymentRepository.delete(order.getPayment());

                throw new PaymentHandler(ResponseCode.NOT_PAID);
            }

            // 결제금액
            Long price = order.getPayment().getPrice();
            // 실결제금액
            int iamportPrice = iamportResponse.getResponse().getAmount().intValue();

            // 결제 금액 검증
            if (iamportPrice != price) {
                orderRepository.delete(order);
                paymentRepository.delete(order.getPayment());

                // Iamport에서 결제금액 위변조로 의심되는 결제 금액 취소
                iamportClient.cancelPaymentByImpUid(new CancelData(iamportResponse.getResponse().getImpUid(), true, new BigDecimal(iamportPrice)));

                throw new PaymentHandler(ResponseCode.SUSPICIOUS_PRICE);
            }

            order.getPayment().changePaymentStatus(PaymentStatus.OK, iamportResponse.getResponse().getImpUid());

            return iamportResponse;

        } catch (IamportResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BaseResponseDto<PaymentResponseDto> getPayInfo(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new PaymentHandler(ResponseCode.ORDER_NOT_FOUND));

        Member member = order.getMember();

        PaymentResponseDto paymentResponseDto = PaymentResponseDto.builder()
                .merchantUid(order.getOrderUID())
                .name("NiorNear 주문 결제")
                .amount(order.getTotalPrice())
                .buyerEmail(member.getEmail())
                .buyerName(member.getName())
                .buyerTel(member.getPhone())
                .build();

        order.getPayment().updatePaymentStatus(PaymentStatus.OK);

        return BaseResponseDto.onSuccess(paymentResponseDto, ResponseCode.OK);
    }
}
