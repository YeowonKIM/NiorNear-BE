package nior_near.server.domain.letter.api;

import lombok.RequiredArgsConstructor;
import nior_near.server.domain.letter.application.LetterService;
import nior_near.server.domain.letter.dto.response.LetterResponseDto;
import nior_near.server.global.common.BaseResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/letters")
public class LetterController {

    private final LetterService letterService;

    // @Operation(summary = "편지함 전체 조회")
    @GetMapping
    BaseResponseDto<List<LetterResponseDto>> getAllLetters() {

        return null;
    }
}
