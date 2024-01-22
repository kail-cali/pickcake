package co.pickcake.common.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable @Getter
@NoArgsConstructor
public class Address {
    private String city;
    private String street;
    private String zipcode;

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }




    public static Address createAddress(String city, String street, String zipcode) {
        Address address = new Address();
        address.setCity(city);
        address.setStreet(street);
        address.setZipcode(zipcode);
        return address;
    }
}
