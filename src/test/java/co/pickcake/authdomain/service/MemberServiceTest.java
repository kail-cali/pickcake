package co.pickcake.authdomain.service;

import co.pickcake.aop.util.ErrorCode;
import co.pickcake.authdomain.entity.Member;
import co.pickcake.authdomain.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Test
    @DisplayName("회원 가입")
    public void signIn() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("newKim");


        //when

        Long saveId = memberService.join(member);


        //then

        Assertions.assertThat(member).isEqualTo(memberRepository.findOne(saveId));
    }

    @Test
    @DisplayName("중복 회원 예외 처리")
    public void signInDuplicate() throws Exception {
        //given
        Member origMember = new Member();
        origMember.setUsername("DuplicateUser");
        Member duplicateMember = new Member();
        duplicateMember.setUsername("DuplicateUser");

        //when

        memberService.join(origMember);
        IllegalStateException thrownError =
                assertThrows(IllegalStateException.class,
                        () -> memberService.join(duplicateMember));

        //then
        Assertions.assertThat(thrownError.getMessage())
                .isEqualTo(ErrorCode.DUPLICATED_USER_ALREADY_EXISTS.toString());



    }

}