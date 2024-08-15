package nior_near.server.domain.store.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MenuAddRequestDto {

    private String menuName;
    private Integer menuOneServing;
    private String menuIntroduction;
    private MultipartFile menuImage;
    /**
     * FIXME: 추후에 로그인 기능 완성되면 빠질 것
     */
    private Long memberId;

}
