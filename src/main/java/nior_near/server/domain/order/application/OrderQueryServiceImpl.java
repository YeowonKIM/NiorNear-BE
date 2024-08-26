package nior_near.server.domain.order.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.order.dto.response.OrderGetResponseDto;
import nior_near.server.domain.order.entity.Order;
import nior_near.server.domain.order.entity.OrderMenu;
import nior_near.server.domain.order.exception.handler.OrderHandler;
import nior_near.server.domain.order.repository.OrderRepository;
import nior_near.server.domain.user.entity.Member;
import nior_near.server.domain.user.repository.MemberRepository;
import nior_near.server.global.common.BaseResponseDto;
import nior_near.server.global.common.ResponseCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    @Override
    public BaseResponseDto<OrderGetResponseDto> getOrder(Long memberId, Long orderId) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new OrderHandler(ResponseCode.MEMBER_NOT_FOUND));
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderHandler(ResponseCode.ORDER_NOT_FOUND));

        // 지금 주문서를 확인할 권한이 있는지(주문한 당사자인지 체크)
        if (!order.getMember().equals(member)) {
            throw new OrderHandler(ResponseCode.ORDER_UNAUTHORIZED);
        }

        List<OrderGetResponseDto.OrderMenuItem> orderMenus = order.getOrderMenuList().stream().map(OrderGetResponseDto.OrderMenuItem::new).toList();

        OrderGetResponseDto orderGetResponseDto = OrderGetResponseDto.builder()
                .orderStatus(order.getStatus())
                .placeAddress(order.getPlace().getAddress() + " " + order.getPlace().getName())
                .totalPrice(order.getTotalPrice())
                .requestMessage(order.getRequestMessage())
                .storePhone(order.getPhone())
                .orderMenus(orderMenus).build();

        return BaseResponseDto.onSuccess(orderGetResponseDto, ResponseCode.OK);
    }
}
