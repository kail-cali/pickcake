package co.pickcake.authdomain.service;

import co.pickcake.aop.util.exception.AlreadyExistUserException;
import co.pickcake.authdomain.dto.SignupRequest;
import co.pickcake.authdomain.entity.Member;
import co.pickcake.authdomain.repository.MemberRepository;
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


}
