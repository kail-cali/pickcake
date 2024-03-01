package co.pickcake.authdomain.service;

import co.pickcake.aop.util.ErrorCode;
import co.pickcake.authdomain.entity.Member;
import co.pickcake.authdomain.repository.MemberRepository;
import co.pickcake.testconfig.TestDataSize;
import co.pickcake.util.TestInitDB;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired private TestInitDB testInitDB;
    @Autowired private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("데이터 검증[success]: 회원 가입")
    @Transactional
    public void signIn() throws Exception {
        //given
        TestDataSize testDataSize = testInitDB.dbInitWithMember();
        //when
        List<Member> members = memberService.findMembers();
        System.out.println("members = " + members);
        //then
        Assertions.assertThat(members.size()).isEqualTo(testDataSize.getSize());

            // -> build test 때, 유저 두개로 잡히는데 디버깅 필요
    }
    @Test
    @DisplayName("데이터 검증[fail]: 중복 회원 예외 처리")
    @Transactional
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