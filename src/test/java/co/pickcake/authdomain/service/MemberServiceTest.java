package co.pickcake.authdomain.service;

import co.pickcake.aop.util.ErrorCode;
import co.pickcake.authdomain.entity.Member;
import co.pickcake.authdomain.repository.MemberRepository;
import co.pickcake.testconfig.TestDataSize;
import co.pickcake.util.TestInitDB;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private TestInitDB testInitDB;

    @Test
    @DisplayName("데이터 검증[success]: 회원 가입")
    public void signIn() throws Exception {
        //given
        TestDataSize testDataSize = testInitDB.dbInitWithMember();
        //when
        List<Member> members = memberService.findMembers();
        //then
        Assertions.assertThat(members.size()).isEqualTo(testDataSize.getSize());

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