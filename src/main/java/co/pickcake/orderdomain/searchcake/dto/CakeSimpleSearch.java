package co.pickcake.orderdomain.searchcake.dto;


import co.pickcake.orderdomain.entity.item.Cake;
import co.pickcake.orderdomain.entity.item.EventCakeCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CakeSimpleSearch {

    private String name;
    private String brand; // -> shop info
    private CakeProfileImageDto profile;
    private int price;


    public CakeSimpleSearch(Cake c) {
        name = c.getName();
        brand = c.getBrand();
        price = c.getPrice();
        profile = new CakeProfileImageDto(c.getCakeImages());


//        imagePath = c.getCakeImages().getProfileImage().getStoreFileName();
    }

}
