package nior_near.server.domain.order.application;

import nior_near.server.domain.order.dto.request.OrderAddRequestDto;
import nior_near.server.domain.order.dto.response.OrderAddResponseDto;
import nior_near.server.global.common.BaseResponseDto;

public interface OrderCommandService {

    BaseResponseDto<OrderAddResponseDto> addOrder(String memberName, OrderAddRequestDto orderAddRequestDto);

}
