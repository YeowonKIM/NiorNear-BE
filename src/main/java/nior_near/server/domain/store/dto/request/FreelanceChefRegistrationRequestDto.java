package nior_near.server.domain.store.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FreelanceChefRegistrationRequestDto {
    private String name;
    private String shortDescription; // title
    private String detailedDescription; // introduction
    private Boolean qualification;
    private Long auth;
    private MultipartFile letter;
    private String placeName;
    private String placeAddress;
    private Long regionId;
    private String message;
    /**
     * FIXME: 추후에 로그인 기능 완성되면 빠질 것
     */
    private Long memberId;
}
