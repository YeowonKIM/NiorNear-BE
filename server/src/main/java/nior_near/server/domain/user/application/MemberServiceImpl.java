package nior_near.server.domain.user.application;

import lombok.RequiredArgsConstructor;
import nior_near.server.domain.letter.application.LetterService;
import nior_near.server.domain.letter.dto.response.LetterResponseDto;
import nior_near.server.domain.user.dto.response.MyMemberResponseDto;
import nior_near.server.domain.user.entity.Member;
import nior_near.server.domain.user.exception.handler.MemberExceptionHandler;
import nior_near.server.domain.user.repository.MemberRepository;
import nior_near.server.global.common.ResponseCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final LetterService letterService;

    @Override
    public MyMemberResponseDto getMyProfile() {

        final int MY_PAGE_LETTER_LIMIT = 3;
        final int DEFAULT_PAGE = 0;

        // TODO: JWT Token으로 member 정보 가져오기
        long memberId = 0;

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberExceptionHandler(ResponseCode.MEMBER_NOT_FOUND));

        List<LetterResponseDto> letters = letterService.getAllLetters(DEFAULT_PAGE, MY_PAGE_LETTER_LIMIT);

        return MyMemberResponseDto.builder()
                .memberId(memberId)
                .nickname(member.getName())
                .point(member.getPoint())
                .imageUrl(member.getProfileImage())
                .letterResponseDtos(letters)
                .build();
    }
}
