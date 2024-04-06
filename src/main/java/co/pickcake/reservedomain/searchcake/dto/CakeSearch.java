package co.pickcake.reservedomain.searchcake.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class CakeSearch {

    private String cakeName;
    private String categoryName;
    private String brand;

}
