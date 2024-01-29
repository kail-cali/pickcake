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
    private String imagePath;
    private int price;


    /* ##XXX##
     * static method 인데 삭제 예정 */
    public CakeSimpleSearch(String name, String brand, String imagePath, int price) {
        this.name = name;
        this.brand = brand;
        this.imagePath = imagePath;
        this.price = price;
    }

    public CakeSimpleSearch(Cake c) {
        name = c.getName();
        brand = c.getBrand();
        imagePath = c.getImagePath();
        price = c.getPrice();
    }

}
