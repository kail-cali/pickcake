package co.pickcake.orderdomain.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter @Setter
public class CakeForm {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String brand;
    private String description;
    private String imagePath;
    private String hashtag;


}
