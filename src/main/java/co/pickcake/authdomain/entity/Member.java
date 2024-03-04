package co.pickcake.authdomain.entity;

import co.pickcake.aop.datetime.AuditOnTime;
import co.pickcake.common.entity.Address;
import co.pickcake.recommend.entity.Heart;
import co.pickcake.shopdomain.entity.Shop;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Entity @Getter
public class Member extends AuditOnTime {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String userId; // 우선 email 로 통일,  소셜 로그인 때 수정 예정
    private String username; // user name 으로 필드로만 사용하고 있음
    private String password; // encoded password, 정책은 WebSecurityConfig 확인

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member")
    private Shop shop;

    @Column(name="PROVIDER_TYPE", length = 20)
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @Embedded
    private Address address;
    /* 수정 메서드 */
    public void setUsername(String username) {
        this.username = username;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public void setUserID(String userId) {
        this.userId = userId;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setProviderType(ProviderType providerType) {
        this.providerType = providerType;
    }
    /* api */
    /* 좋아요 relation 에 대한 연관 관계를 단방향 매핑으로 수정
     * 양방향으로 매핑할 경우, 얻을 수 있는 장점은 유저가 삭제될 때, 좋아요 정보도 날릴 수 있다는 점인데,
     * 삭제는 별도 dml로 관리하는 것이 JPA 쿼리 예측에 편하고 데이터 활용성을 위해 좋아요 relation 을 누적하기로 함.
     * 좋아요 relation에 유효하지 않은 member_id 가 있을 수 있다는 점만 entity 에 명시하도록 변경.
    * */
    @OneToMany(mappedBy = "member")
    private List<Heart> hearts = new ArrayList<>();
    /* 연관 관계 편의 메서드 */

    /* 생성 편의 메서드 */
    public static Member createMember(String username, String userId, String password, String city, String street, String zipcode) {
        Member member = new Member();
        member.setPassword(password);
        if ( username == null) {
            member.setUsername(userId);
        } else {
            member.setUsername(username);
        }
        member.setUserID(userId);
        member.setAddress(Address.createAddress(city, street, zipcode));
        member.setProviderType(ProviderType.NATIVE);
        return member;
    }
    public static Member createMember(String username, String userId, String password) {
        Member member = new Member();
        member.setPassword(password);
        if ( username == null) {
            member.setUsername(userId);
        } else {
            member.setUsername(username);
        }
        member.setUserID(userId);
        member.setProviderType(ProviderType.NATIVE);
        return member;
    }
    /* 3rd-party social login only */
    public static Member createMember(String username, String userId, String password, ProviderType providerType) {
        Member member = new Member();
        member.setPassword(password);
        if ( username == null) {
            member.setUsername(userId);
        } else {
            member.setUsername(username);
        }
        member.setUserID(userId);
        member.setProviderType(providerType);
        return member;
    }


    public void changePassword(String newPassword) {
        password = newPassword;
    }
}
