package co.pickcake.authdomain.api;

import co.pickcake.authdomain.dto.SignupRequest;
import co.pickcake.authdomain.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthApiController {

    private final AuthService authService;
    @PostMapping("/signup")
    public void signup(@RequestBody @Validated SignupRequest request) {
        authService.signup(request);
    }

}
