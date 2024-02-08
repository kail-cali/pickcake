package co.pickcake.authdomain.service;

import co.pickcake.aop.util.ErrorCode;
import co.pickcake.authdomain.entity.Member;
import co.pickcake.authdomain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        validateDuplicatedMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicatedMember(Member member) {
        /* 중복회원 검증 로직 */
        List<Member> findMembers = memberRepository.findByUsername(member.getUsername());
        if (! findMembers.isEmpty()) {
            throw  new IllegalStateException(ErrorCode.DUPLICATED_USER_ALREADY_EXISTS.toString());
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findById(Long memberID) {
        return memberRepository.findById(memberID).get();
    }

    @Transactional
    public void update(Long id, String name) {
        Member one = findById(id);
        one.setUsername(name);
    }

}
