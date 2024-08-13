package nior_near.server.domain.payment.application;

import com.siot.IamportRestClient.IamportClient;
import lombok.RequiredArgsConstructor;
import nior_near.server.domain.order.entity.Order;
import nior_near.server.domain.order.repository.OrderRepository;
import nior_near.server.domain.payment.dto.request.OrderPayRequestDto;
import nior_near.server.domain.payment.dto.response.OrderPayResponseDto;
import nior_near.server.domain.payment.exception.handler.PaymentHandler;
import nior_near.server.domain.payment.repository.PaymentRepository;
import nior_near.server.global.common.BaseResponseDto;
import nior_near.server.global.common.ResponseCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final IamportClient iamportClient;

    @Override
    public BaseResponseDto<OrderPayResponseDto> findRequestDto(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new PaymentHandler(ResponseCode.PAYMENT_NOT_FOUND));

        // 추가 혹은 삭제 예정
        OrderPayRequestDto orderPayRequest = OrderPayRequestDto.builder()
                .orderId(orderId)
                .clientName(order.getMember().getName())
                .totalPrice(order.getTotalPrice())
                .clientNumber(order.getPhone())
                .build();

        return BaseResponseDto.onSuccess(OrderPayResponseDto.builder().orderId(order.getId().toString()).build(), ResponseCode.OK);
    }

}
