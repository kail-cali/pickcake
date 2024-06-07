package co.pickcake.config;


import co.pickcake.auth.domain.Member;
import co.pickcake.auth.repository.MemberRepository;
import co.pickcake.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;


@RequiredArgsConstructor
public class TestMockSecurityContext implements WithSecurityContextFactory<TestMockUser> {
    private final MemberRepository memberRepository;
    private final AuthService authService;

    @Override
    public SecurityContext createSecurityContext(TestMockUser annotation) {

        Member member = Member.createMember(annotation.userName(), annotation.email(), annotation.password());
        memberRepository.save(member);
        UserPrincipal userPrincipal = new UserPrincipal(member);
        SimpleGrantedAuthority roleAdmin = new SimpleGrantedAuthority(annotation.role());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userPrincipal, member.getPassword(),
                List.of(roleAdmin));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(token);
        return context;
    }
}
