package nior_near.server.domain.store.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.store.application.HomeService;
import nior_near.server.domain.store.dto.response.HomeResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {
    private final HomeService homeService;

    @GetMapping("")
    public HomeResponseDto getHome(@RequestParam(required = false) Long regionId) {
        return homeService.getHome(regionId);
    }
}
