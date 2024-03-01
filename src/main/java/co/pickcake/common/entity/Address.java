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
    @Override
    public String toString() {
        return city+ " " + street + " " +zipcode;
    }
    public String toSimpleString() {
        return city +" " + street;
    }
    public static Address createAddress(String city, String street, String zipcode) {
        Address address = new Address();
        address.city = city;
        address.street = street;
        address.zipcode = zipcode;
        return address;
    }


}
