package nior_near.server.domain.letter.application;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import nior_near.server.domain.letter.dto.request.ThankLetterRequestDto;
import nior_near.server.domain.letter.dto.response.LetterResponseDto;
import nior_near.server.domain.letter.dto.response.ThankLetterResponseDto;
import nior_near.server.domain.letter.entity.Letter;
import nior_near.server.domain.letter.entity.LetterStatus;
import nior_near.server.domain.letter.exception.handler.LetterExceptionHandler;
import nior_near.server.domain.letter.repository.LetterRepository;
import nior_near.server.domain.user.entity.Member;
import nior_near.server.domain.user.repository.MemberRepository;
import nior_near.server.global.common.ResponseCode;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LetterServiceImpl implements LetterService {

    private final LetterRepository letterRepository;
    private final MemberRepository memberRepository;
    private final int AllLettersLimit = 30;

    @Override
    public List<LetterResponseDto> getAllLetters() {

        // TODO: JWT 토큰에서 사용자 정보 가져오기
        long memberId = 0L;

        // TODO: 사용자가 없는 경우 예외처리

        LocalDateTime startDate = LocalDateTime.now().minusYears(1);
        Pageable pageable = PageRequest.of(0, AllLettersLimit);

        // 해당 userId가 receiver이고 생성일이 1년이 되지 않았고, 그리고 최대 LIMIT 개의 편지를 조회
        List<Letter> letters = letterRepository.findAllByReceiverId(memberId, startDate, pageable);

        return letters.stream()
                .map(LetterResponseDto::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ThankLetterResponseDto registerThankLetter(ThankLetterRequestDto thankLetterDto) {

        // TODO: JWT 토큰에서 사용자 정보 가져오기
        long memberId = 0L;

        // FIXME: Member Handler로 변경
        Member member = memberRepository.findById(memberId)
                .orElseThrow(EntityNotFoundException::new);

        Member receiver = memberRepository.findById(thankLetterDto.getReceiverId())
                .orElseThrow(EntityNotFoundException::new);

        Letter letter = Letter.builder()
                .senderName(member.getName())
                .text(thankLetterDto.getContent())
                .status(LetterStatus.UNREAD)
                .sender(member)
                .receiver(receiver)
                .build();

        Letter updatedLetter = letterRepository.save(letter);

        return ThankLetterResponseDto.builder()
                .letterId(updatedLetter.getId())
                .build();
    }

    @Override
    @Transactional
    public Long updateLetterStatus(Long letterId) {

        Letter letter = letterRepository.findById(letterId)
                .orElseThrow(() -> new LetterExceptionHandler(ResponseCode.LETTER_NOT_FOUND));

        letter.setStatus(LetterStatus.READ);

        return letterRepository.save(letter).getId();
    }
}
