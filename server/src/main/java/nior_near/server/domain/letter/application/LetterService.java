package nior_near.server.domain.letter.application;

import nior_near.server.domain.letter.dto.response.LetterResponseDto;

import java.util.List;

public interface LetterService {

    List<LetterResponseDto> getAllLetters();
}
