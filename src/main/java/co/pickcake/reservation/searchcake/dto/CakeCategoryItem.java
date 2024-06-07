package co.pickcake.reservation.searchcake.dto;


import co.pickcake.reservation.domain.item.EventCakeCategory;
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
