package nior_near.server.domain.order.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.order.application.OrderCommandService;
import nior_near.server.domain.order.application.OrderQueryService;
import nior_near.server.domain.order.dto.request.OrderAddRequestDto;
import nior_near.server.domain.order.dto.response.OrderAddResponseDto;
import nior_near.server.domain.order.dto.response.OrderGetResponseDto;
import nior_near.server.global.common.BaseResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;

    @PostMapping
    public BaseResponseDto<OrderAddResponseDto> addOrder(@ModelAttribute OrderAddRequestDto orderAddRequestDto) {

        /**
         * TODO: 추후에 accessToken 에서 받아올 정보
         */
//        String memberName = memberService.retrieveName(request);
        Long memberId = 11L;

        return orderCommandService.addOrder(memberId, orderAddRequestDto);

    }

    @GetMapping("/{orderId}")
    public BaseResponseDto<OrderGetResponseDto> getOrder(@PathVariable("orderId") Long orderId) {

        /**
         * TODO: 추후에 accessToken 에서 받아올 정보
         */
//        String memberName = memberService.retrieveName(request);
        Long memberId = 11L;

        return orderQueryService.getOrder(memberId, orderId);
    }
}
