package nior_near.server.domain.order.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.order.application.OrderCommandService;
import nior_near.server.domain.order.application.OrderQueryService;
import nior_near.server.domain.order.dto.request.OrderAddRequestDto;
import nior_near.server.domain.order.dto.response.OrderAddResponseDto;
import nior_near.server.domain.order.dto.response.OrderGetResponseDto;
import nior_near.server.global.common.BaseResponseDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;

    @PostMapping
    public BaseResponseDto<OrderAddResponseDto> addOrder(@Valid @ModelAttribute OrderAddRequestDto orderAddRequestDto, @AuthenticationPrincipal UserDetails userDetails) {

        return orderCommandService.addOrder(userDetails.getUsername(), orderAddRequestDto);

    }

    @GetMapping("/{orderId}")
    public BaseResponseDto<OrderGetResponseDto> getOrder(@PathVariable("orderId") Long orderId, @AuthenticationPrincipal UserDetails userDetails) {

        return orderQueryService.getOrder(userDetails.getUsername(), orderId);
    }
}
