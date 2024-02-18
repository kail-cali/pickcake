package co.pickcake.authdomain.service;


import co.pickcake.authdomain.entity.Member;
import co.pickcake.authdomain.repository.MemberRepository;
import co.pickcake.config.UserPrincipal;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> member = Optional.ofNullable(memberRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found")));
        return new UserPrincipal(member.get());
    }

}
