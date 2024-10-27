package nior_near.server.domain.letter.application;

import nior_near.server.domain.letter.dto.request.ThankLetterRequestDto;
import nior_near.server.domain.letter.dto.response.LetterResponseDto;
import nior_near.server.domain.letter.dto.response.ThankLetterResponseDto;

import java.util.List;

public interface LetterService {

    List<LetterResponseDto> getAllLetters(int page, int limit, String memberName);
    ThankLetterResponseDto registerThankLetter(ThankLetterRequestDto thankLetterDto, String memberName);
    Long updateLetterStatus(Long letterId);
}
