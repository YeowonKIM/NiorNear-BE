package nior_near.server.domain.letter.application;

import lombok.RequiredArgsConstructor;
import nior_near.server.domain.letter.dto.response.LetterResponseDto;
import nior_near.server.domain.letter.entity.Letter;
import nior_near.server.domain.letter.repository.LetterRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LetterServiceImpl implements LetterService {

    private final LetterRepository letterRepository;
    private final int AllLettersLimit = 30;

    @Override
    public List<LetterResponseDto> getAllLetters() {

        // TODO: JWT 토큰에서 사용자 정보 가져오기
        long userId = 0L;

        LocalDateTime startDate = LocalDateTime.now().minusYears(1);
        Pageable pageable = PageRequest.of(0, AllLettersLimit);

        // 해당 userId가 receiver이고 생성일이 1년이 되지 않았고, 그리고 최대 LIMIT 개의 편지를 조회
        List<Letter> letters = letterRepository.findALlByReceiverId(userId, startDate, pageable);

        return letters.stream()
                .map(LetterResponseDto::of)
                .collect(Collectors.toList());
    }
}
