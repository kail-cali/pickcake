package co.pickcake.reservedomain.searchcake.dto;


import co.pickcake.reservedomain.entity.item.Cake;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CakeSimpleSearch {

    private String name;
    private String brand; // -> shop info
    private CakeProfileImageDto profile;
    private Integer price;


    public CakeSimpleSearch(Cake c) {
        name = c.getName();
        brand = c.getBrand();
        price = c.getPrice();
        profile = new CakeProfileImageDto(c.getCakeImages());
    }

}
