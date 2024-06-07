package co.pickcake.auth.api;

import co.pickcake.auth.dto.MemberUpdateRequest;
import co.pickcake.auth.dto.SignupRequest;
import co.pickcake.auth.service.AuthService;
import co.pickcake.config.UserPrincipal;
import co.pickcake.config.WebSecurityConfig;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthApiController {

    private final AuthService authService;
    private final WebSecurityConfig securityConfig;

    @PostMapping("/signup")
    public void signup(@RequestBody @Validated SignupRequest request) {
        authService.signup(request);
    }
    @PreAuthorize("hasRole('ROLE_USER') && hasPermission(#memberId, 'POST', 'PATCH')")
    @PatchMapping("/o/{memberId}")
    public void update(@AuthenticationPrincipal UserPrincipal principal,
                       @PathVariable(name="memberId") Long memberId,
                       @RequestBody @Valid MemberUpdateRequest request) {
        String encoded = securityConfig.passwordEncoder().encode(request.getNewPassword());
        request.setNewPassword(encoded); /* 이건 좀 고민이 필요, 어디선가는 인코딩 해야함;; */
        authService.update(principal, request);
    }
    @DeleteMapping("/o/{memberId}")
    @PreAuthorize("hasRole('ROLE_USER') && hasPermission(#memberId, 'POST', 'DELETE')")
    public void withDrawl(@AuthenticationPrincipal UserPrincipal principal,
            @PathVariable("memberId") Long memberId) {
        authService.delete(principal, memberId);
    }
}
