package co.pickcake.authdomain.entity;

import co.pickcake.common.entity.Address;
import jakarta.persistence.*;
import lombok.Getter;





@Entity @Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    public void setUsername(String username) {
        this.username = username;
    }

    private String  userID;
    private String username;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "private_info_id")
    private PrivateInfo privateInfo;

    @Embedded
    private Address address;

    /* 수정 메서드 */
    public void setAddress(Address address) {
        this.address = address;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    private void setPrivateInfo(PrivateInfo privateInfo) {
        this.privateInfo = privateInfo;
    }
    /* api */
    /* 좋아요 relation 에 대한 연관 관계를 단방향 매핑으로 수정
     * 양방향으로 매핑할 경우, 얻을 수 있는 장점은 유저가 삭제될 때, 좋아요 정보도 날릴 수 있다는 점인데,
     * 삭제는 별도 dml로 관리하는 것이 JPA 쿼리 예측에 편하고 데이터 활용성을 위해 좋아요 relation 을 누적하기로 함.
     * 좋아요 relation에 유효하지 않은 member_id 가 있을 수 있다는 점만 entity 에 명시하도록 변경.
     * @OneToMany(mappedBy = "member")
     * private List<Heart> hearts = new ArrayList<>();
    * */

    /* 연관 관계 편의 메서드 */

    /* 생성 편의 메서드 */
    public static Member createMember(String name, String city, String street, String zipcode) {
        Member newMember = new Member();
        newMember.setUsername(name);
        newMember.setAddress(Address.createAddress(city, street, zipcode));
        return newMember;
    }

    public static Member createMember(String username, String userID, String password, String city, String street, String zipcode) {
        Member member = new Member();
        PrivateInfo privateInfo = PrivateInfo.create(password);
        member.setPrivateInfo(privateInfo);
        member.setUsername(username);
        member.setUserID(userID);
        member.setAddress(Address.createAddress(city, street, zipcode));
        return member;
    }

    /* TODO 비즈니스 로직 , 추후 추가 예정 */
    public boolean changePassword() {
        return true;
    }
}
