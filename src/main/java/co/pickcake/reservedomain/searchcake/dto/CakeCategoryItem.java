package co.pickcake.reservedomain.searchcake.dto;


import co.pickcake.reservedomain.entity.item.EventCakeCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CakeCategoryItem {

    @JsonIgnore
    private Long categoryId;

    @NotBlank
    private String categoryName;
    public CakeCategoryItem(EventCakeCategory eventCakeCategory) {
        this.categoryName = eventCakeCategory.getCakeCategory().getName();
    }
}
