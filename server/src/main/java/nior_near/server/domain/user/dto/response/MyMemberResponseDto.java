package nior_near.server.domain.user.dto.response;

import lombok.Builder;
import lombok.Data;
import nior_near.server.domain.user.entity.Member;

@Data
@Builder
public class MyMemberResponseDto {

    private long memberId;
    private String nickname;
    private long point;
    private String imageUrl;
    // TODO: 편지함 Dtos 추가

    public static MyMemberResponseDto of(Member member) {

        return MyMemberResponseDto.builder()
                .memberId(member.getId())
                .nickname(member.getName())
                .point(member.getPoint())
                .imageUrl(member.getProfileImage())
                .build();
    }
}
