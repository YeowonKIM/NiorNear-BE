package nior_near.server.domain.user.dto.response;

import lombok.Builder;
import lombok.Data;
import nior_near.server.domain.letter.dto.response.LetterResponseDto;

import java.util.List;

@Data
@Builder
public class MyMemberResponseDto {

    private long memberId;
    private String nickname;
    private long point;
    private String imageUrl;
    private List<LetterResponseDto> letterResponseDtos;
}
