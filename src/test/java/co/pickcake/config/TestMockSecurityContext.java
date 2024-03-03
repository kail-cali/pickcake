package co.pickcake.config;


import co.pickcake.authdomain.entity.Member;
import co.pickcake.authdomain.repository.MemberRepository;
import co.pickcake.authdomain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;


@RequiredArgsConstructor
public class TestMockSecurityContext implements WithSecurityContextFactory<TestMockUser> {
    /* 정황 상 db 컨테이너 하나 추가로 더 생성하는 곳이 이것 때문인 것 같은데 시큐리티 관련된 테스트가 아니라면 크게 상관이 없어보임 */
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
