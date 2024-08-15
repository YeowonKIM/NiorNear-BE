package nior_near.server.domain.user.application;

import jakarta.servlet.http.HttpServletRequest;
import nior_near.server.domain.user.dto.response.MyMemberResponseDto;
import nior_near.server.domain.user.entity.Member;

public interface MemberService {

    MyMemberResponseDto getMyProfile();

    public Member findMemberByName(String name);
    public String retrieveName(HttpServletRequest request);

    public void updateMemberRegion(Member member, Long regionId);

}
