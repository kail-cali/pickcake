package co.pickcake.config;

import co.pickcake.auth.domain.Member;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserPrincipal extends User {

    private final Long userId;
    public UserPrincipal(Member member) {

        super(member.getUserId(), member.getPassword(),
                List.of(
                        new SimpleGrantedAuthority("ROLE_USER")
//                        new SimpleGrantedAuthority("READ")
                ));
        this.userId = member.getId();
    }

    public Long getUserId() {
        return userId;
    }
}
