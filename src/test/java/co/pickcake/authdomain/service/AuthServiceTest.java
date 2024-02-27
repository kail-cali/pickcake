package co.pickcake.authdomain.service;

import co.pickcake.aop.util.exception.UserNotFoundException;
import co.pickcake.authdomain.dto.MemberUpdateRequest;
import co.pickcake.authdomain.entity.Member;
import co.pickcake.authdomain.repository.MemberRepository;

import co.pickcake.common.entity.Address;
import co.pickcake.config.UserPrincipal;
import co.pickcake.testconfig.TestDataItem;
import co.pickcake.testconfig.TestDataSize;
import co.pickcake.util.TestInitDB;
import jakarta.persistence.TransactionRequiredException;
import net.bytebuddy.asm.Advice;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private TestInitDB testInitDB;  // 메서드 단위 트랜잭션 관리가 필요합니다. 제약사항 참고
    @Autowired
    private MockMvc mockMvc;
    @Test
    @DisplayName("기능테스트[success]: 회원 정보 수정 시 정상 동작 확인")
    @Transactional
    public void updateTest() {
        //given
        TestDataItem testDataItem = testInitDB.dbInitWithSingleItem();
        Member member1 = (Member) testDataItem.getItems().get("member1");
        String oldPassword = member1.getPassword();
        String oldUserName = member1.getUsername();
        Address oldAddress = member1.getAddress();
        MemberUpdateRequest request = MemberUpdateRequest.create("new@email.com", "new1234",
                "new-Hail", Address.createAddress("seoul"," yonseiro", "33333"));
        UserPrincipal principal = new UserPrincipal(member1);
        //when
        authService.update(principal, request);
        Member updateMember = memberRepository.findById(member1.getId()).orElseThrow(UserNotFoundException::new);
        //then
        assertThat(member1.getId()).isEqualTo(updateMember.getId());
        assertThat(updateMember.getPassword()).isNotEmpty();
        assertThat(oldPassword).isNotEqualTo(updateMember.getPassword());
        assertThat(oldUserName).isNotEqualTo(updateMember.getUsername());
        assertThat(oldAddress).isNotEqualTo(updateMember.getAddress());
    }

    @Test
    @DisplayName("기능테스트[success]: 회원 정보 수정 시 createAt 확인 ")
    @Transactional
    public void updateTestWithDate() {
        //given
        LocalDateTime start = LocalDateTime.now();
        TestDataItem testDataItem = testInitDB.dbInitWithSingleItem();
        Member member1 = (Member) testDataItem.getItems().get("member1");

                String oldPassword = member1.getPassword();
        String oldUserName = member1.getUsername();
        Address oldAddress = member1.getAddress();
        MemberUpdateRequest request = MemberUpdateRequest.create("new@email.com", "new1234",
                "new-Hail", Address.createAddress("seoul"," yonseiro", "33333"));
        UserPrincipal principal = new UserPrincipal(member1);
        //when

        authService.update(principal, request);
        Member updateMember = memberRepository.findById(member1.getId()).orElseThrow(UserNotFoundException::new);
        //then
        assertThat(member1.getId()).isEqualTo(updateMember.getId());
        assertThat(updateMember.getPassword()).isNotEmpty();
        assertThat(oldPassword).isNotEqualTo(updateMember.getPassword());
        assertThat(oldUserName).isNotEqualTo(updateMember.getUsername());
        assertThat(oldAddress).isNotEqualTo(updateMember.getAddress());

        assertThat(member1.getCreateAt()).isAfter(start);

    }


    @Test
    @DisplayName("기능테스트[success]: 회원 정보 삭제 시 정상 동작 확인")
    @Transactional
    public void deleteTest() {
        //given
        TestDataItem testDataItem = testInitDB.dbInitWithSingleItem();
        Member member1 = (Member) testDataItem.getItems().get("member1");
        String oldPassword = member1.getPassword();
        String oldUserName = member1.getUsername();
        Address oldAddress = member1.getAddress();

        UserPrincipal principal = new UserPrincipal(member1);
        //when
        authService.delete(principal, member1.getId());
        //then
        assertThatThrownBy(
                () -> memberRepository.findById(member1.getId()).orElseThrow(UserNotFoundException::new))
                .isInstanceOf(UserNotFoundException.class);
    }



}