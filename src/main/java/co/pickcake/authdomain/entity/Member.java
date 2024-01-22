package co.pickcake.authdomain.entity;

import co.pickcake.common.entity.Address;
import co.pickcake.recommand.entity.Like;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Entity @Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    public void setUsername(String username) {
        this.username = username;
    }

    //    @Column
    private String username;

    private String password;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "private_info_id")
    private PrivateInfo privateInfo;



    @Embedded
    private Address address;

//    @OneToMany(mappedBy="member")
//    private List<Order> orderList = new ArrayList<>();

    /* 수정 메서드 */
    public void setAddress(Address address) {
        this.address = address;
    }

    /* API */
    /* member 가 좋아요한 리스트 */
    @OneToMany(mappedBy = "member")
    private List<Like> likelist = new ArrayList<>();

}
