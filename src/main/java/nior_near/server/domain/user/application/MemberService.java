package nior_near.server.domain.user.application;

import jakarta.servlet.http.HttpServletRequest;
import nior_near.server.domain.user.dto.response.MyMemberResponseDto;
import nior_near.server.domain.user.dto.response.MyPaymentSummaryResponseDto;
import nior_near.server.domain.user.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    MyMemberResponseDto getMyProfile();

    List<MyPaymentSummaryResponseDto> getMyPaymentSummary();

    public Member findMemberByName(String name);

    public void updateMemberRegion(Member member, Long regionId);

//    String retrieveName(HttpServletRequest request);

    Optional<Member> findMemberById(Long userId);
}
