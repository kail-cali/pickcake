package co.pickcake.auth.service;

import co.pickcake.aop.util.exception.AbnormalUserAccessException;
import co.pickcake.aop.util.exception.AlreadyExistUserException;
import co.pickcake.aop.util.exception.UserNotFoundException;
import co.pickcake.auth.dto.MemberUpdateRequest;
import co.pickcake.auth.dto.SignupRequest;
import co.pickcake.auth.domain.Member;
import co.pickcake.auth.repository.MemberRepository;
import co.pickcake.config.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequest request) {
        /* need to fix */
        validateDuplicatedMember(request.getUserId());
        String encoded = passwordEncoder.encode(request.getPassword());
        Member member;
        if (request.getAddress() == null) {
            member = Member.createMember(request.getUserName(), request.getUserId(), encoded,
                    request.getAddress().getCity(), request.getAddress().getStreet(), request.getAddress().getZipcode());
        } else {
            member = Member.createMember(request.getUserName(), request.getUserId(), encoded);
        }
        memberRepository.save(member);

    }
    private void validateDuplicatedMember(String userId) {
        /* 중복회원 검증 로직 */
        Optional<Member> byUserID = memberRepository.findByUserId(userId);
        if (byUserID.isPresent()) {
            throw new AlreadyExistUserException();
        }
    }
    @Transactional
    public void update(UserPrincipal principal, MemberUpdateRequest request) {
        Member member = memberRepository.findById(principal.getUserId())
                .orElseThrow(UserNotFoundException::new);
        if (principal.getUserId().equals(member.getId())) {
            member.setPassword(request.getNewPassword());
            member.setUsername(request.getUserName());
            if (request.getAddress() != null) {
                member.setAddress(request.getAddress());
            }
            memberRepository.save(member);
        } else {
            throw new AbnormalUserAccessException(); /* 정상적이지 않는 접근, Toke 탈취 혹은 유저의 url 강제 수정 */
        }
    }

    @Transactional
    public void delete(UserPrincipal principal, Long id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        if (principal.getUserId().equals(member.getId())) {
            memberRepository.delete(member);
        } else {
            throw new AbnormalUserAccessException();
        }
    }

}
