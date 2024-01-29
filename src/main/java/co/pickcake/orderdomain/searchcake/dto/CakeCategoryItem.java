package co.pickcake.orderdomain.searchcake.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CakeCategoryItem {

    @JsonIgnore
    private Long categoryId;

    private String categoryName;

}
