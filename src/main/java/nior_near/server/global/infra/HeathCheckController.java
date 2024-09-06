package nior_near.server.global.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HeathCheckController {

    @GetMapping("/healthcheck")
    public ResponseEntity healthCheck() {
        return ResponseEntity.ok(200);
    }

}
