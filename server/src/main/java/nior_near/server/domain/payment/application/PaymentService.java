package nior_near.server.domain.payment.application;

import nior_near.server.domain.payment.dto.request.OrderPayRequestDto;
import nior_near.server.domain.payment.dto.response.OrderPayResponseDto;
import nior_near.server.global.common.BaseResponseDto;

public interface PaymentService {
    // 결제 요청 데이터 조회
    BaseResponseDto<OrderPayResponseDto> findRequestDto(Long id);
}
