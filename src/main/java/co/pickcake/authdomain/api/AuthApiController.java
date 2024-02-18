package co.pickcake.authdomain.api;

import co.pickcake.authdomain.dto.SignupRequest;
import co.pickcake.authdomain.service.AuthService;
import co.pickcake.config.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthApiController {

    private final AuthService authService;

    @GetMapping("/signin")
    public String signin(@AuthenticationPrincipal UserPrincipal userPrincipal) {

        return "redirect:/";
    }

    @PostMapping("/signup")
    public void signup(@RequestBody @Validated SignupRequest request) {
        authService.signup(request);
    }

}
