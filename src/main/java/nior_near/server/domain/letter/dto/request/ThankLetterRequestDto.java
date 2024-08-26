package nior_near.server.domain.letter.dto.request;

import lombok.Data;

@Data
public class ThankLetterRequestDto {

    private String content;
    private long receiverId;
}
