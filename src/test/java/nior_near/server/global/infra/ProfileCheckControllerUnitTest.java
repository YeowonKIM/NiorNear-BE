package nior_near.server.global.infra;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProfileCheckControllerUnitTest {
    @Test
    public void port_profile_이_조회된다() {
        //given
        String expectedProfile = "real";
        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("oauth");
        env.addActiveProfile("real-db");

        ProfileCheckController profileCheckController = new ProfileCheckController(env);

        //when
        String profile = profileCheckController.profileCheck();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    public void port_profile이_없으면_첫_번째가_조회된다() {
        //given
        String expectedProfile = "oauth";
        MockEnvironment env = new MockEnvironment();

        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("real-db");

        ProfileCheckController controller = new ProfileCheckController(env);

        //when
        String profile = controller.profileCheck();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    public void active_profile이_없으면_default가_조회된다() {
        //given
        String expectedProfile = "default";
        MockEnvironment env = new MockEnvironment();
        ProfileCheckController controller = new ProfileCheckController(env);

        //when
        String profile = controller.profileCheck();

        //then
        assertThat(profile).isEqualTo(expectedProfile);
    }
}