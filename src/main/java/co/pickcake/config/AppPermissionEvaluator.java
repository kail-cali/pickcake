package co.pickcake.config;


import co.pickcake.aop.util.exception.AuthAccessDeniedException;
import co.pickcake.auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
@Slf4j
@RequiredArgsConstructor
public class AppPermissionEvaluator implements PermissionEvaluator {

    private final MemberRepository memberRepository;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {

        var principal = (UserPrincipal) authentication.getPrincipal();

        var member = memberRepository.findById( (Long) principal.getUserId())
                .orElseThrow(AuthAccessDeniedException::new);
        if (member.getId().equals((Long) principal.getUserId())) {
            return true;
        }
        log.info("[인가실패] 불가한 접근 입니다. ");
        return false;
    }
}
