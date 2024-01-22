package co.pickcake.authdomain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;


/* PUT METHODS BASED TABLE */
@Entity @Getter
public class PrivateInfo {

    @Id
    @GeneratedValue
    @Column(name = "private_info_id")
    private Long id;

    private String password;



}
