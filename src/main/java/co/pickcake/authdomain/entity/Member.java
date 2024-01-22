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

}
