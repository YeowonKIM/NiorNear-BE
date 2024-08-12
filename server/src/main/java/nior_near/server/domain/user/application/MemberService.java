package nior_near.server.domain.user.application;

import nior_near.server.domain.user.dto.response.MyMemberResponseDto;

public interface MemberService {

    MyMemberResponseDto getMyProfile();
}
