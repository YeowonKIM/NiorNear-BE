package nior_near.server.domain.user.application;

import lombok.RequiredArgsConstructor;
import nior_near.server.domain.user.dto.response.MyMemberResponseDto;
import nior_near.server.domain.user.entity.Member;
import nior_near.server.domain.user.exception.handler.MemberExceptionHandler;
import nior_near.server.domain.user.repository.MemberRepository;
import nior_near.server.global.common.ResponseCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final int MyPageLettersLimit = 3;

    @Override
    public MyMemberResponseDto getMyProfile() {

        // TODO: JWT Token으로 member 정보 가져오기
        long memberId = 0;

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberExceptionHandler(ResponseCode.MEMBER_NOT_FOUND));

        // TODO: getAllLetters 메서드에 limit 매개변수를 추가하고 LetterDtos를 가져옴.

        return MyMemberResponseDto.builder()
                .memberId(memberId)
                .nickname(member.getName())
                .point(member.getPoint())
                .imageUrl(member.getProfileImage())
                .build();
    }
}
