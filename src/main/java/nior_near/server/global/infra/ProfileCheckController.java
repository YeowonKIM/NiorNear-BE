package nior_near.server.global.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfileCheckController {

    private final Environment env;

    @GetMapping("/profile-check")
    public String profileCheck() {
        List<String> profiles = Arrays.asList(env.getActiveProfiles());
        List<String> portProfiles = Arrays.asList("port1", "port2");

        String defaultProfiles = profiles.isEmpty() ? "default" : profiles.get(0);

        return profiles.stream().filter(portProfiles::contains).findAny().orElse(defaultProfiles);

    }

}
