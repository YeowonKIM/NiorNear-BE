package nior_near.server.domain.order.application;

import nior_near.server.domain.order.dto.response.OrderGetResponseDto;
import nior_near.server.global.common.BaseResponseDto;

public interface OrderQueryService {

    BaseResponseDto<OrderGetResponseDto> getOrder(Long memberId, Long orderId);
}
